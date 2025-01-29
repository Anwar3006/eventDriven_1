package com.eventdriven.cms.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eventdriven.cms.requestDTO.PostDTO;
import com.eventdriven.cms.services.BlogPostService;

import lombok.AllArgsConstructor;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.eventdriven.cms.domain.BlogPost;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;




@RestController
@AllArgsConstructor
@RequestMapping("/api/v1")
public class BlogPostController {
    
    private final BlogPostService blogPostService;

    @PostMapping("/posts")
    public ResponseEntity<BlogPost> CreatePost(@RequestBody PostDTO body) {
        BlogPost newPost = blogPostService.createPost(body);
        return new ResponseEntity<>(newPost, HttpStatus.CREATED);
    }
    
    @GetMapping("/posts/all")
    public ResponseEntity<List<BlogPost>> GetAllPosts() {
        return new ResponseEntity<>(blogPostService.getAllPosts(), HttpStatus.OK);
    }
    
    @GetMapping("/posts/{id}")
    public ResponseEntity<?> GetPostById(@PathVariable Long id) {
        Optional<BlogPost> postFound = blogPostService.getPostById(id);
        if(postFound.isEmpty()){
            return new ResponseEntity<>("Post not found", HttpStatus.NOT_FOUND);
        } 
        return new ResponseEntity<>(postFound.get(), HttpStatus.OK);
    }
    
    // Search Functionality maybe
    @GetMapping("/posts")
    public ResponseEntity<?> GetPostByAuthor(@RequestParam(name="authorId") Long authorId){
        Optional<List<BlogPost>> postsFound = blogPostService.getPostByAuthor(authorId);
        if(postsFound.isEmpty()){
            return new ResponseEntity<>("Post not found", HttpStatus.NOT_FOUND);
        } 
        return new ResponseEntity<>(postsFound.get(), HttpStatus.OK);
    }

    @DeleteMapping("/posts/{id}")
    public ResponseEntity<?> DeletePostById(@PathVariable Long id) {
        blogPostService.deletePost(id); 
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/posts/{id}")
    public ResponseEntity<?> UpdatePostById(@PathVariable Long id, @RequestBody PostDTO body) {
        BlogPost entity = blogPostService.updatePost(body, id); 
        return new ResponseEntity<>(entity, HttpStatus.OK);
    }
}
