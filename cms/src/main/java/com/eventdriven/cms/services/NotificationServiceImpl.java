package com.eventdriven.cms.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.eventdriven.cms.domain.AppUser;
import com.eventdriven.cms.domain.Notification;
import com.eventdriven.cms.domain.NotificationType;
import com.eventdriven.cms.repository.AppUserRepository;
import com.eventdriven.cms.repository.NotificationRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final AppUserRepository userRepository;

    @Override
    public void createNotification(AppUser user, String message, NotificationType noType) {
        Notification newNotice = new Notification();
        newNotice.setMessage(message);
        newNotice.setUser(user);
        newNotice.setType(noType);
        
        notificationRepository.save(newNotice);
    }

    @Override
    public List<Notification> getUserNotifications(Long userId) {
        return notificationRepository.findByUserIdOrderByCreatedAtDesc(userId);
    }
    
}
