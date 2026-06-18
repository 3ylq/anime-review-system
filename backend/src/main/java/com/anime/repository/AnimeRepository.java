package com.anime.repository;

import com.anime.entity.Anime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnimeRepository extends JpaRepository<Anime, Long> {

    Page<Anime> findByTitleContaining(String title, Pageable pageable);

    Page<Anime> findByCategory(String category, Pageable pageable);

    @Query("SELECT a FROM Anime a ORDER BY a.rating DESC")
    Page<Anime> findTopByRating(Pageable pageable);

    @Query("SELECT a FROM Anime a ORDER BY a.favoriteCount DESC")
    Page<Anime> findMostPopular(Pageable pageable);

    @Query("SELECT DISTINCT a.category FROM Anime a")
    List<String> findAllCategories();

    List<Anime> findByStatusOrderByCreatedAtDesc(String status);
}