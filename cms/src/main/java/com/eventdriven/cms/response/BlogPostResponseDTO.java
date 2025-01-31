package com.eventdriven.cms.response;

import java.util.List;
import java.util.stream.Collectors;

import com.eventdriven.cms.domain.AppUser;
import com.eventdriven.cms.domain.BlogPost;

import lombok.Data;

@Data
public class BlogPostResponseDTO {

    private Long id;
    private String title;
    private String content;
    private String author;
    private String status;
    private List<Long> subscribers;


    public BlogPostResponseDTO setPostResponse(BlogPost post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.author = post.getAuthor().getName();
        this.status = post.getStatus().toString();
        this.subscribers = post.getSubscribers().stream().map(AppUser::getId).collect(Collectors.toList());
        return this;
    }

}
