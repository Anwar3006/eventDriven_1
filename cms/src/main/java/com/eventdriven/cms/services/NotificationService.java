package com.eventdriven.cms.services;

import java.util.List;

import com.eventdriven.cms.domain.AppUser;
import com.eventdriven.cms.domain.Notification;
import com.eventdriven.cms.domain.NotificationType;

public interface NotificationService {
    
    public void createNotification(AppUser user, String message, NotificationType noType);

    public List<Notification> getUserNotifications(Long userId);
}
