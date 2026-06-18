package com.anime.service;

import com.anime.dto.AnimeDTO;
import com.anime.entity.Anime;
import com.anime.repository.AnimeRepository;
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
public class AnimeService {

    private final AnimeRepository animeRepository;

    public Anime createAnime(AnimeDTO animeDTO) {
        Anime anime = Anime.builder()
                .title(animeDTO.getTitle())
                .description(animeDTO.getDescription())
                .coverImage(animeDTO.getCoverImage())
                .category(animeDTO.getCategory())
                .author(animeDTO.getAuthor())
                .director(animeDTO.getDirector())
                .studio(animeDTO.getStudio())
                .episodeCount(animeDTO.getEpisodeCount())
                .airDate(animeDTO.getAirDate())
                .endDate(animeDTO.getEndDate())
                .status(animeDTO.getStatus())
                .characters(animeDTO.getCharacters())
                .rating(0.0)
                .reviewCount(0)
                .viewCount(0)
                .favoriteCount(0)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        return animeRepository.save(anime);
    }

    public Anime getAnimeById(Long id) {
        Anime anime = animeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("动漫不存在"));
        
        anime.setViewCount(anime.getViewCount() + 1);
        return animeRepository.save(anime);
    }

    public Page<Anime> searchAnime(String keyword, Pageable pageable) {
        return animeRepository.findByTitleContaining(keyword, pageable);
    }

    public Page<Anime> getAnimeByCategory(String category, Pageable pageable) {
        return animeRepository.findByCategory(category, pageable);
    }

    public Page<Anime> getTopRatedAnime(Pageable pageable) {
        return animeRepository.findTopByRating(pageable);
    }

    public Page<Anime> getMostPopularAnime(Pageable pageable) {
        return animeRepository.findMostPopular(pageable);
    }

    public List<String> getAllCategories() {
        return animeRepository.findAllCategories();
    }

    public Anime updateAnime(Long id, AnimeDTO animeDTO) {
        Anime anime = getAnimeById(id);

        if (animeDTO.getTitle() != null) {
            anime.setTitle(animeDTO.getTitle());
        }
        if (animeDTO.getDescription() != null) {
            anime.setDescription(animeDTO.getDescription());
        }
        if (animeDTO.getCoverImage() != null) {
            anime.setCoverImage(animeDTO.getCoverImage());
        }

        anime.setUpdatedAt(LocalDateTime.now());
        return animeRepository.save(anime);
    }

    public void deleteAnime(Long id) {
        animeRepository.deleteById(id);
    }
}