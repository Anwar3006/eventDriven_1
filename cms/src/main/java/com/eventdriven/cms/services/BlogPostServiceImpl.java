package com.eventdriven.cms.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.eventdriven.cms.domain.AppUser;
import com.eventdriven.cms.domain.BlogPost;
import com.eventdriven.cms.domain.BlogPostEvent;
import com.eventdriven.cms.domain.BlogPostEvent.EventType;
import com.eventdriven.cms.event.EventDispatcher;
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
    private final EventDispatcher eventDispatcher;

    @Transactional
    @Override
    public BlogPost createPost(PostDTO post) {
        BlogPost newPost = new BlogPost();

        newPost.setTitle(post.getTitle());
        newPost.setContent(post.getContent());
        newPost.setStatus(post.getStatus());

        AppUser user = userRepository.findById(post.getAuthor()).orElseThrow(() -> new RuntimeException("User not found"));
        newPost.setAuthor(user);

        BlogPost savedPost = blogPostRepository.save(newPost);

        //dispatch event
        BlogPostEvent mutatedPost = new BlogPostEvent(savedPost.getId(), savedPost.getAuthor().getId(), savedPost.getSubscribers().stream().map(sub -> sub.getId()).toList(), EventType.NEW_POST);
        eventDispatcher.dispatch(mutatedPost);

        return savedPost;
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
    public void deletePost(Long id, String authorEmail) {
        BlogPost postToDelete = verifyAuthor(id, authorEmail);
       blogPostRepository.deleteById(postToDelete.getId());

       //dispatch event
       BlogPostEvent mutated = new BlogPostEvent(postToDelete.getId(), postToDelete.getAuthor().getId(), postToDelete.getSubscribers().stream().map(sub -> sub.getId()).toList(), EventType.DELETE);
       eventDispatcher.dispatch(mutated);
    }

    @Override
    @Transactional
    public BlogPost updatePost(PostDTO post, Long id, String authorEmail) {
        System.out.println("Author: " + authorEmail);
        BlogPost postToUpdate = verifyAuthor(id, authorEmail);

        boolean isUpdated = false;

        if(postToUpdate.getTitle() != null && !postToUpdate.getTitle().equals(post.getTitle())){
            postToUpdate.setTitle(post.getTitle());
            isUpdated = true;
        }

        if(postToUpdate.getContent() != null && !postToUpdate.getContent().equals(post.getContent())){
            postToUpdate.setContent(post.getContent());
            isUpdated = true;
        }
        
        if(postToUpdate.getStatus() != null && !postToUpdate.getStatus().equals(post.getStatus())){
            postToUpdate.setStatus(post.getStatus());
            isUpdated = true;
        }
        
        if (isUpdated){
            blogPostRepository.save(postToUpdate);
        }

        // dispatch event
        BlogPostEvent mutated = new BlogPostEvent(postToUpdate.getId(), postToUpdate.getAuthor().getId(), postToUpdate.getSubscribers().stream().map(sub -> sub.getId()).toList(), EventType.UPDATE);
        eventDispatcher.dispatch(mutated);

        return postToUpdate;
    }

    @Override
    public BlogPost verifyAuthor(Long id, String authorEmail) {
        BlogPost post = blogPostRepository.findById(id).orElseThrow(() -> new RuntimeException("Post not found"));
        AppUser user = userRepository.findByEmail(authorEmail).orElseThrow(() -> new RuntimeException("User not found"));
        if(user.getId() != post.getAuthor().getId()){
            throw new RuntimeException("You are not authorized to delete this post");
        }

        return post;
    }

}
