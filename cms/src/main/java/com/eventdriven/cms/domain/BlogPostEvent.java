package com.eventdriven.cms.domain;

import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.RequiredArgsConstructor;


@Data
@RequiredArgsConstructor
/**
 * Represents the event that will be dispatched when:
 *  - Post is created
 *  - Post is updated
 *  - Post is deleted
 * Users that are subscribed to the post will then receive notifications
 */
public class BlogPostEvent implements Serializable{
    
    private final Long postId;
    private final Long authorId;
    private final List<Long> subscriberIds;
    private final EventType eventType;

    public enum EventType{
        NEW_POST,
        UPDATE,
        DELETE
    }
}
