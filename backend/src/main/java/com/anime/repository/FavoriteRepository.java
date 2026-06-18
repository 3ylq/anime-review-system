package com.anime.repository;

import com.anime.entity.Favorite;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, Long> {

    Page<Favorite> findByUserId(Long userId, Pageable pageable);

    Optional<Favorite> findByUserIdAndAnimeId(Long userId, Long animeId);

    Boolean existsByUserIdAndAnimeId(Long userId, Long animeId);

    void deleteByUserIdAndAnimeId(Long userId, Long animeId);
}
