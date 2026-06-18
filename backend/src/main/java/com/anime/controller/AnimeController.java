package com.anime.controller;

import com.anime.dto.AnimeDTO;
import com.anime.entity.Anime;
import com.anime.service.AnimeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/anime")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AnimeController {

    private final AnimeService animeService;

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllAnime(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<Anime> animes = animeService.getTopRatedAnime(pageable);
            return ResponseEntity.ok(buildResponse(animes));
        } catch (Exception e) {
            return buildErrorResponse(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getAnimeById(@PathVariable Long id) {
        try {
            Anime anime = animeService.getAnimeById(id);
            Map<String, Object> response = new HashMap<>();
            response.put("data", anime);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return buildErrorResponse(e.getMessage());
        }
    }

    @GetMapping("/search")
    public ResponseEntity<Map<String, Object>> searchAnime(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<Anime> animes = animeService.searchAnime(keyword, pageable);
            return ResponseEntity.ok(buildResponse(animes));
        } catch (Exception e) {
            return buildErrorResponse(e.getMessage());
        }
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<Map<String, Object>> getAnimeByCategory(
            @PathVariable String category,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<Anime> animes = animeService.getAnimeByCategory(category, pageable);
            return ResponseEntity.ok(buildResponse(animes));
        } catch (Exception e) {
            return buildErrorResponse(e.getMessage());
        }
    }

    @GetMapping("/top-rated")
    public ResponseEntity<Map<String, Object>> getTopRatedAnime(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<Anime> animes = animeService.getTopRatedAnime(pageable);
            return ResponseEntity.ok(buildResponse(animes));
        } catch (Exception e) {
            return buildErrorResponse(e.getMessage());
        }
    }

    @GetMapping("/most-popular")
    public ResponseEntity<Map<String, Object>> getMostPopularAnime(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<Anime> animes = animeService.getMostPopularAnime(pageable);
            return ResponseEntity.ok(buildResponse(animes));
        } catch (Exception e) {
            return buildErrorResponse(e.getMessage());
        }
    }

    @GetMapping("/categories")
    public ResponseEntity<Map<String, Object>> getAllCategories() {
        try {
            List<String> categories = animeService.getAllCategories();
            Map<String, Object> response = new HashMap<>();
            response.put("data", categories);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return buildErrorResponse(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> createAnime(@Valid @RequestBody AnimeDTO animeDTO) {
        try {
            Anime anime = animeService.createAnime(animeDTO);
            Map<String, Object> response = new HashMap<>();
            response.put("message", "动漫创建成功");
            response.put("data", anime);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return buildErrorResponse(e.getMessage());
        }
    }

    private Map<String, Object> buildResponse(Page<Anime> page) {
        Map<String, Object> response = new HashMap<>();
        response.put("data", page.getContent());
        response.put("currentPage", page.getNumber());
        response.put("totalPages", page.getTotalPages());
        response.put("totalElements", page.getTotalElements());
        return response;
    }

    private ResponseEntity<Map<String, Object>> buildErrorResponse(String message) {
        Map<String, Object> response = new HashMap<>();
        response.put("error", message);
        return ResponseEntity.badRequest().body(response);
    }
}
