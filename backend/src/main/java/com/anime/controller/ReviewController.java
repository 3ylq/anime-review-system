package com.anime.controller;

import com.anime.dto.ReviewDTO;
import com.anime.entity.Review;
import com.anime.security.JwtTokenProvider;
import com.anime.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/review")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ReviewController {

    private final ReviewService reviewService;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/create")
    public ResponseEntity<Map<String, Object>> createReview(
            @RequestHeader("Authorization") String token,
            @Valid @RequestBody ReviewDTO reviewDTO) {
        try {
            Long userId = jwtTokenProvider.getUserIdFromToken(token);
            Review review = reviewService.createReview(userId, reviewDTO.getAnimeId(), reviewDTO);
            Map<String, Object> response = new HashMap<>();
            response.put("message", "评论创建成功");
            response.put("data", review);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return buildErrorResponse(e.getMessage());
        }
    }

    @GetMapping("/anime/{animeId}")
    public ResponseEntity<Map<String, Object>> getReviewsByAnime(
            @PathVariable Long animeId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<Review> reviews = reviewService.getReviewsByAnime(animeId, pageable);
            return buildPageResponse(reviews);
        } catch (Exception e) {
            return buildErrorResponse(e.getMessage());
        }
    }

    private ResponseEntity<Map<String, Object>> buildPageResponse(Page<Review> page) {
        Map<String, Object> response = new HashMap<>();
        response.put("data", page.getContent());
        response.put("currentPage", page.getNumber());
        response.put("totalPages", page.getTotalPages());
        response.put("totalElements", page.getTotalElements());
        return ResponseEntity.ok(response);
    }

    private ResponseEntity<Map<String, Object>> buildErrorResponse(String message) {
        Map<String, Object> response = new HashMap<>();
        response.put("error", message);
        return ResponseEntity.badRequest().body(response);
    }
}