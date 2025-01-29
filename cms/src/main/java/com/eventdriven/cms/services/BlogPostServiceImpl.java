package com.eventdriven.cms.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.eventdriven.cms.domain.AppUser;
import com.eventdriven.cms.domain.BlogPost;
import com.eventdriven.cms.repository.AppUserRepository;
import com.eventdriven.cms.repository.BlogPostRepository;
import com.eventdriven.cms.requestDTO.PostDTO;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class BlogPostServiceImpl implements BlogPostService{

    private final BlogPostRepository blogPostRepository;
    private final AppUserRepository userRepository;

    @Transactional
    @Override
    public BlogPost createPost(PostDTO post) {
        BlogPost newPost = new BlogPost();

        newPost.setTitle(post.getTitle());
        newPost.setContent(post.getContent());
        newPost.setStatus(post.getStatus());

        AppUser user = userRepository.findById(post.getAuthor()).orElseThrow(() -> new RuntimeException("User not found"));
        newPost.setAuthor(user);

        return blogPostRepository.save(newPost);
    }

    @Override
    public Optional<BlogPost> getPostById(Long id) {
        return blogPostRepository.findById(id);
    }

    @Override
    public Optional<List<BlogPost>> getPostByAuthor(Long authorId) {
        return blogPostRepository.getPostByAuthorId(authorId);
    }

    @Override
    public List<BlogPost> getAllPosts() {
        return blogPostRepository.findAll();
    }

    @Override
    public void deletePost(Long id) {
        BlogPost postToDelete = blogPostRepository.findById(id).orElseThrow(() -> new RuntimeException("Post not found"));
       blogPostRepository.deleteById(postToDelete.getId());
    }

    @Override
    public BlogPost updatePost(PostDTO post, Long id) {
        BlogPost postToUpdate = blogPostRepository.findById(id).get();
        postToUpdate.setTitle(post.getTitle());
        postToUpdate.setContent(post.getContent());
        postToUpdate.setStatus(post.getStatus());
        blogPostRepository.save(postToUpdate);
        return postToUpdate;
    }

}
