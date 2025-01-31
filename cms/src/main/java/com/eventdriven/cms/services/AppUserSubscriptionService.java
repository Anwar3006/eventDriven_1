package com.eventdriven.cms.services;


public interface AppUserSubscriptionService {
    
    void addSubscriber(String userEmail, Long postId);

    void removeSubscriber(String userEmail, Long postId);

    boolean hasSubscriber(Long userId, Long postId);
}
