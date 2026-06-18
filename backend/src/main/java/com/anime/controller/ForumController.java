package com.anime.controller;

import com.anime.entity.ForumPost;
import com.anime.security.JwtTokenProvider;
import com.anime.service.ForumService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/forum")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ForumController {

    private final ForumService forumService;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/post")
    public ResponseEntity<Map<String, Object>> createPost(
            @RequestHeader("Authorization") String token,
            @RequestBody ForumPost post) {
        try {
            Long userId = jwtTokenProvider.getUserIdFromToken(token);
            ForumPost created = forumService.createPost(userId, post);
            Map<String, Object> response = new HashMap<>();
            response.put("message", "帖子发布成功");
            response.put("data", created);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return buildErrorResponse(e.getMessage());
        }
    }

    @GetMapping("/recommended")
    public ResponseEntity<Map<String, Object>> getRecommendedPosts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<ForumPost> posts = forumService.getRecommendedPosts(pageable);
            return buildPageResponse(posts);
        } catch (Exception e) {
            return buildErrorResponse(e.getMessage());
        }
    }

    @GetMapping("/hot")
    public ResponseEntity<Map<String, Object>> getHotPosts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<ForumPost> posts = forumService.getHotPosts(pageable);
            return buildPageResponse(posts);
        } catch (Exception e) {
            return buildErrorResponse(e.getMessage());
        }
    }

    private ResponseEntity<Map<String, Object>> buildPageResponse(Page<ForumPost> page) {
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