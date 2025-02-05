package com.eventdriven.cms.services;

import java.util.List;
import java.util.Optional;

import com.eventdriven.cms.domain.BlogPost;
import com.eventdriven.cms.requestDTO.PostDTO;

public interface BlogPostService {

    public BlogPost createPost(PostDTO post);

    public Optional<BlogPost> getPostById(Long id);
    Optional<BlogPost> getPostByTitle(String title);

    public Optional<List<BlogPost>> getPostByAuthor(Long authorId);

    public List<BlogPost> getAllPosts();

    public void deletePost(Long id, String authorEmail);

    public BlogPost updatePost(PostDTO post, Long id, String authorEmail);

    public BlogPost verifyAuthor(Long id, String authorEmail);
}
