package com.eventdriven.cms.event;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.eventdriven.cms.domain.BlogPost;
import com.eventdriven.cms.domain.BlogPostEvent;
import com.eventdriven.cms.domain.NotificationType;
import com.eventdriven.cms.repository.AppUserRepository;
import com.eventdriven.cms.repository.BlogPostRepository;
import com.eventdriven.cms.services.NotificationService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class EventConsumer {
    
    private final NotificationService notificationService;
    private final BlogPostRepository postRepository;
    private final AppUserRepository userRepository;


    @RabbitListener(queues = "${rabbitmq.queue.notification}")
    public void handlePostEvent(BlogPostEvent event){
        log.info("Received a blogPost event: {}", event);

        BlogPost post = postRepository.findById(event.getPostId()).get();
        String message = createNotificationMessage(event, post.getTitle());

        NotificationType type;
        switch (event.getEventType()) {
            case BlogPostEvent.EventType.NEW_POST:
                type = NotificationType.NEW_POST_CREATED;
                break;
            
            case BlogPostEvent.EventType.UPDATE:
                type = NotificationType.POST_UPDATED;
                break;
            
            case BlogPostEvent.EventType.DELETE:
                type = NotificationType.POST_DELETED;
                break;
        
            default:
                type = null;
                break;
        }

        event.getSubscriberIds().forEach(id -> {
            userRepository.findById(id).ifPresent(user -> {
                notificationService.createNotification(user, message, type);

                //Email service/Push Notification service will send the notification
                log.info("Notification: Sending an email to user: {}", user.getName());
            });
        });
    }


    private String createNotificationMessage(BlogPostEvent event, String title){
        switch (event.getEventType()) {
            case BlogPostEvent.EventType.NEW_POST:
                return String.format("A new Blog post - '%s' - has been created", title);
            
            case BlogPostEvent.EventType.UPDATE:
                return String.format("Blog post '%s' has been updated", title);
            
            case BlogPostEvent.EventType.DELETE:
                return String.format("Blog post '%s' has been deleted", title);
        
            default:
                return String.format("Blog post event type not supported");
        }
    }
}
