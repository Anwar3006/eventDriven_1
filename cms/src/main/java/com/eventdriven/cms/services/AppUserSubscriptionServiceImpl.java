package com.eventdriven.cms.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.eventdriven.cms.domain.AppUser;
import com.eventdriven.cms.domain.BlogPost;
import com.eventdriven.cms.repository.AppUserRepository;
import com.eventdriven.cms.repository.BlogPostRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AppUserSubscriptionServiceImpl implements AppUserSubscriptionService{

    private final BlogPostRepository postRepository;
    private final AppUserRepository userRepository;

    @Override
    @Transactional
    public void addSubscriber(String userEmail, Long postId) {
        Optional<BlogPost> postFound = postRepository.findById(postId);
        if(postFound.isEmpty()){
            throw new IllegalArgumentException("Post with id: " + postId + " not found");
        }

        Optional<AppUser> userFound = userRepository.findByEmail(userEmail);
        if(userFound.isEmpty()){
            throw new IllegalArgumentException("User with email: " + userEmail + " not found");
        }

        AppUser user = userFound.get();
        BlogPost post = postFound.get();
        post.getSubscribers().add(user);
    }

    @Override
    @Transactional
    public void removeSubscriber(String userEmail, Long postId) {
        Optional<BlogPost> postFound = postRepository.findById(postId);
        if(postFound.isEmpty()){
            throw new IllegalArgumentException("Post with id: " + postId + " not found");
        }

        Optional<AppUser> userFound = userRepository.findByEmail(userEmail);
        if(userFound.isEmpty()){
            throw new IllegalArgumentException("User with email: " + userEmail + " not found");
        }

        AppUser user = userFound.get();
        BlogPost post = postFound.get();
        post.getSubscribers().remove(user);
    }

    @Override
    public boolean hasSubscriber(Long userId, Long postId) {
        Optional<AppUser> userFound = userRepository.findById(userId);
        if(userFound.isEmpty()){
            throw new IllegalArgumentException("User with id: " + userId + " not found");
        }
        
        AppUser user = userFound.get();
        return postRepository.findById(postId).map(post -> post.getSubscribers().contains(user)).orElse(false);
    }
    
}
