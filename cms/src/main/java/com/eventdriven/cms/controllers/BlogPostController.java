package com.eventdriven.cms.controllers;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eventdriven.cms.requestDTO.PostDTO;
import com.eventdriven.cms.response.BlogPostResponseDTO;
import com.eventdriven.cms.services.BlogPostService;

import io.jsonwebtoken.lang.Collections;
import lombok.AllArgsConstructor;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.eventdriven.cms.domain.BlogPost;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;




@RestController
@AllArgsConstructor
@RequestMapping("/api/v1")
public class BlogPostController {
    
    private final BlogPostService blogPostService;
    

    @PostMapping("/posts")
    public ResponseEntity<BlogPostResponseDTO> CreatePost(@RequestBody PostDTO body) {
        BlogPost newPost = blogPostService.createPost(body);

        BlogPostResponseDTO dto = new BlogPostResponseDTO();
        
        return new ResponseEntity<>(dto.setPostResponse(newPost), HttpStatus.CREATED);
    }
    
    @GetMapping("/posts/all")
    public ResponseEntity<List<BlogPostResponseDTO>> GetAllPosts() {
        List<BlogPost> posts = blogPostService.getAllPosts();
        if (posts.isEmpty()){
            return new ResponseEntity<>(Collections.emptyList(), HttpStatus.OK);
        }

        List<BlogPostResponseDTO> response = posts.stream().map(post -> new BlogPostResponseDTO().setPostResponse(post)).collect(Collectors.toList());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    @GetMapping("/posts/{id}")
    public ResponseEntity<?> GetPostById(@PathVariable Long id) {
        Optional<BlogPost> postFound = blogPostService.getPostById(id);
        if(postFound.isEmpty()){
            return new ResponseEntity<>("Post not found", HttpStatus.NOT_FOUND);
        }

        BlogPostResponseDTO dto = new BlogPostResponseDTO();
        return new ResponseEntity<>(dto.setPostResponse(postFound.get()), HttpStatus.OK);
    }
    
    // Search Functionality maybe
    @GetMapping("/posts")
    public ResponseEntity<?> GetPostByAuthor(@RequestParam(name="authorId") Long authorId){
        Optional<List<BlogPost>> postsFound = blogPostService.getPostByAuthor(authorId);
        if(postsFound.isEmpty()){
            return new ResponseEntity<>("Post not found", HttpStatus.NOT_FOUND);
        }

        List<BlogPostResponseDTO> response = postsFound.get().stream().map(post -> new BlogPostResponseDTO().setPostResponse(post)).collect(Collectors.toList());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/posts/{id}")
    public ResponseEntity<?> DeletePostById(@PathVariable Long id, Authentication authObj) {
        try {
            String authorEmail = authObj.getName();
            blogPostService.deletePost(id, authorEmail); 
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(
                "Error deleting post: " + e.getMessage(), 
                HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    @PatchMapping("/posts/{id}")
    public ResponseEntity<?> UpdatePostById(@PathVariable Long id, @RequestBody PostDTO body, Authentication authObj) {
        try {
            String authorEmail = authObj.getName();
        BlogPost entity = blogPostService.updatePost(body, id, authorEmail); 

        BlogPostResponseDTO dto = new BlogPostResponseDTO();
        return new ResponseEntity<>(dto.setPostResponse(entity), HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(
                "Error updating post: " + e.getMessage(), 
                HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }
}
