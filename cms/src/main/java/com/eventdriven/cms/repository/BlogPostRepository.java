package com.eventdriven.cms.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.eventdriven.cms.domain.BlogPost;

@Repository
public interface BlogPostRepository extends JpaRepository<BlogPost, Long>{

    Optional<List<BlogPost>> getPostByAuthorId(Long authorId);

    @Query("SELECT p FROM BlogPost p WHERE LOWER(p.title) = LOWER(:title)")
    Optional<BlogPost> findByTitle(@Param("title") String title);
}
