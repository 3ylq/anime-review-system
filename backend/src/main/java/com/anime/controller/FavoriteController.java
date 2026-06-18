package com.anime.controller;

import com.anime.service.FavoriteService;
import com.anime.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/favorite")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class FavoriteController {

    private final FavoriteService favoriteService;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/{animeId}")
    public ResponseEntity<Map<String, Object>> addFavorite(
            @RequestHeader("Authorization") String token,
            @PathVariable Long animeId) {
        try {
            Long userId = jwtTokenProvider.getUserIdFromToken(token);
            favoriteService.addFavorite(userId, animeId);
            Map<String, Object> response = new HashMap<>();
            response.put("message", "收藏成功");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return buildErrorResponse(e.getMessage());
        }
    }

    @DeleteMapping("/{animeId}")
    public ResponseEntity<Map<String, Object>> removeFavorite(
            @RequestHeader("Authorization") String token,
            @PathVariable Long animeId) {
        try {
            Long userId = jwtTokenProvider.getUserIdFromToken(token);
            favoriteService.removeFavorite(userId, animeId);
            Map<String, Object> response = new HashMap<>();
            response.put("message", "取消收藏成功");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return buildErrorResponse(e.getMessage());
        }
    }

    private ResponseEntity<Map<String, Object>> buildErrorResponse(String message) {
        Map<String, Object> response = new HashMap<>();
        response.put("error", message);
        return ResponseEntity.badRequest().body(response);
    }
}