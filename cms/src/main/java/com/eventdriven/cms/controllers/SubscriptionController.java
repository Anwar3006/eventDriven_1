package com.eventdriven.cms.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eventdriven.cms.services.AppUserSubscriptionService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PutMapping;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/subscription")
public class SubscriptionController {
    
    private final AppUserSubscriptionService subscriptionService;

    @PutMapping("/{id}")
    public ResponseEntity<?> AddSubscriber(@PathVariable Long id, Authentication authObj) {
        try {    
            subscriptionService.addSubscriber(authObj.getName(), id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error subscribing: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> RemoveSubscriber(@PathVariable Long id, Authentication authObj) {
        try {
            subscriptionService.removeSubscriber(authObj.getName(), id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error subscribing: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
