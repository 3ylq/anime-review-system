package com.anime.repository;

import com.anime.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    Page<Review> findByAnimeId(Long animeId, Pageable pageable);

    Page<Review> findByUserId(Long userId, Pageable pageable);

    Review findByAnimeIdAndUserId(Long animeId, Long userId);
}