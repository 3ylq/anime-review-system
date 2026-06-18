package com.anime.service;

import com.anime.dto.ReviewDTO;
import com.anime.entity.Anime;
import com.anime.entity.Review;
import com.anime.entity.User;
import com.anime.repository.AnimeRepository;
import com.anime.repository.ReviewRepository;
import com.anime.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final AnimeRepository animeRepository;
    private final UserRepository userRepository;

    public Review createReview(Long userId, Long animeId, ReviewDTO reviewDTO) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("用户不存在"));
        Anime anime = animeRepository.findById(animeId)
                .orElseThrow(() -> new RuntimeException("动漫不存在"));

        Review review = Review.builder()
                .anime(anime)
                .user(user)
                .content(reviewDTO.getContent())
                .rating(reviewDTO.getRating())
                .likeCount(0)
                .replyCount(0)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        review = reviewRepository.save(review);

        // 更新动漫评分
        updateAnimeRating(animeId);

        return review;
    }

    public Page<Review> getReviewsByAnime(Long animeId, Pageable pageable) {
        return reviewRepository.findByAnimeId(animeId, pageable);
    }

    public Page<Review> getReviewsByUser(Long userId, Pageable pageable) {
        return reviewRepository.findByUserId(userId, pageable);
    }

    public Review updateReview(Long reviewId, ReviewDTO reviewDTO) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("评论不存在"));

        if (reviewDTO.getContent() != null) {
            review.setContent(reviewDTO.getContent());
        }
        if (reviewDTO.getRating() != null) {
            review.setRating(reviewDTO.getRating());
        }

        review.setUpdatedAt(LocalDateTime.now());
        return reviewRepository.save(review);
    }

    public void deleteReview(Long reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("评论不存在"));
        
        Long animeId = review.getAnime().getId();
        reviewRepository.deleteById(reviewId);
        
        // 更新动漫评分
        updateAnimeRating(animeId);
    }

    public void likeReview(Long reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("评论不存在"));
        review.setLikeCount(review.getLikeCount() + 1);
        reviewRepository.save(review);
    }

    private void updateAnimeRating(Long animeId) {
        Anime anime = animeRepository.findById(animeId)
                .orElseThrow(() -> new RuntimeException("动漫不存在"));

        List<Review> reviews = anime.getId() != null ? reviewRepository.findByAnimeId(animeId, null).getContent() : List.of();
        
        if (reviews.isEmpty()) {
            anime.setRating(0.0);
        } else {
            double averageRating = reviews.stream()
                    .mapToDouble(Review::getRating)
                    .average()
                    .orElse(0.0);
            anime.setRating(Math.round(averageRating * 10.0) / 10.0); // 保留一位小数
        }

        anime.setReviewCount(reviews.size());
        animeRepository.save(anime);
    }
}
