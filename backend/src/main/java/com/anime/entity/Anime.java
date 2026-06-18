package com.anime.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "anime")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Anime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "LONGTEXT")
    private String description;

    @Column(columnDefinition = "LONGTEXT")
    private String coverImage;

    private String category;

    private String author;

    private String director;

    private String studio;

    @Column(name = "episode_count")
    private Integer episodeCount;

    @Column(name = "air_date")
    private String airDate;

    @Column(name = "end_date")
    private String endDate;

    private String status; // 更新中/已完结/待上线

    @Column(columnDefinition = "TEXT")
    private String characters;

    private Double rating = 0.0;

    private Integer reviewCount = 0;

    private Integer viewCount = 0;

    private Integer favoriteCount = 0;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        if (this.rating == null) this.rating = 0.0;
        if (this.reviewCount == null) this.reviewCount = 0;
        if (this.viewCount == null) this.viewCount = 0;
        if (this.favoriteCount == null) this.favoriteCount = 0;
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}