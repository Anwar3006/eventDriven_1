package com.eventdriven.cms.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
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
    
    private POST_STATUS status;

    @ManyToOne
    @JoinColumn(name = "author_id", nullable=false)
    private AppUser author;

    public BlogPost(String title, String content, POST_STATUS status, AppUser author) {
        this.title = title;
        this.content = content;
    }
}
