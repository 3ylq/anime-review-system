package com.anime.repository;

import com.anime.entity.ForumPost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ForumPostRepository extends JpaRepository<ForumPost, Long> {

    Page<ForumPost> findByCategory(String category, Pageable pageable);

    Page<ForumPost> findByTitleContaining(String title, Pageable pageable);

    Page<ForumPost> findByUserId(Long userId, Pageable pageable);

    @Query("SELECT p FROM ForumPost p WHERE p.isRecommended = true ORDER BY p.createdAt DESC")
    Page<ForumPost> findRecommended(Pageable pageable);

    @Query("SELECT p FROM ForumPost p WHERE p.isHot = true ORDER BY p.viewCount DESC")
    Page<ForumPost> findHotPosts(Pageable pageable);
}