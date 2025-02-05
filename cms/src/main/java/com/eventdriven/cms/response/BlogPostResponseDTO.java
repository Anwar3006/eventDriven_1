package com.eventdriven.cms.response;

import java.time.LocalDateTime;
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
    private Author author;
    private String status;
    private List<Long> subscribers;
    private String excerpt;
    private String coverImg;
    private LocalDateTime createdAt;


    public BlogPostResponseDTO setPostResponse(BlogPost post) {
        Author author = new Author();
        author.setName(post.getAuthor().getName());
        author.setAvatarUrl(post.getAuthor().getAvatarUrl());

        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.author = author;
        this.status = post.getStatus().toString();
        this.excerpt = post.getExcerpt();
        this.createdAt = post.getCreatedAt();
        this.coverImg = post.getCoverImg();
        this.subscribers = post.getSubscribers().stream().map(AppUser::getId).collect(Collectors.toList());
        return this;
    }

    @Data
    private class Author{
        private String name;
        private String avatarUrl;
    }

}
