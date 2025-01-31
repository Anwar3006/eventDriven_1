package com.eventdriven.cms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eventdriven.cms.domain.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Long>{
 
    List<Notification> findByUserIdOrderByCreatedAtDesc(Long userId);
}
