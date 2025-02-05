package com.eventdriven.cms.domain;


import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.validation.constraints.NotBlank;

import lombok.Data;
import lombok.NoArgsConstructor;

// The BlogPost class - represents a blog post made by an author
@Entity
@Data
@NoArgsConstructor
public class BlogPost {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    
    @NotBlank(message = "Title is required")
    private String title;
    
    @NotBlank(message = "Content is required")
    private String content;

    private String excerpt;
    
    @Enumerated(EnumType.STRING)
    private POST_STATUS status;

    private String coverImg;

    @ManyToOne
    @JoinColumn(name = "author_id", nullable=false)
    private AppUser author;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        excerpt = content.substring(0, Math.min(content.length(), 100));
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    @ManyToMany
    @JoinTable(name = "post_subscribers", joinColumns = @JoinColumn(name = "post_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<AppUser> subscribers = new HashSet<>();

    public BlogPost(String title, String content, POST_STATUS status, AppUser author) {
        this.title = title;
        this.content = content;
        this.status = status;
        this.author = author;

        this.coverImg = "";
    }
}
