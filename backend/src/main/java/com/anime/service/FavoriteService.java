package com.anime.service;

import com.anime.entity.Anime;
import com.anime.entity.Favorite;
import com.anime.entity.User;
import com.anime.repository.AnimeRepository;
import com.anime.repository.FavoriteRepository;
import com.anime.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class FavoriteService {

    private final FavoriteRepository favoriteRepository;
    private final UserRepository userRepository;
    private final AnimeRepository animeRepository;

    public void addFavorite(Long userId, Long animeId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("用户不存在"));
        Anime anime = animeRepository.findById(animeId)
                .orElseThrow(() -> new RuntimeException("动漫不存在"));

        if (favoriteRepository.existsByUserIdAndAnimeId(userId, animeId)) {
            throw new RuntimeException("已经收藏过");
        }

        Favorite favorite = Favorite.builder()
                .user(user)
                .anime(anime)
                .createdAt(LocalDateTime.now())
                .build();

        favoriteRepository.save(favorite);
        anime.setFavoriteCount(anime.getFavoriteCount() + 1);
        animeRepository.save(anime);
    }

    public void removeFavorite(Long userId, Long animeId) {
        if (!favoriteRepository.existsByUserIdAndAnimeId(userId, animeId)) {
            throw new RuntimeException("未收藏此动漫");
        }

        favoriteRepository.deleteByUserIdAndAnimeId(userId, animeId);
        Anime anime = animeRepository.findById(animeId)
                .orElseThrow(() -> new RuntimeException("动漫不存在"));
        anime.setFavoriteCount(Math.max(0, anime.getFavoriteCount() - 1));
        animeRepository.save(anime);
    }

    public Page<Favorite> getUserFavorites(Long userId, Pageable pageable) {
        return favoriteRepository.findByUserId(userId, pageable);
    }

    public Boolean isFavorited(Long userId, Long animeId) {
        return favoriteRepository.existsByUserIdAndAnimeId(userId, animeId);
    }
}