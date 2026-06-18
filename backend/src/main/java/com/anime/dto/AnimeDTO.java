package com.anime.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AnimeDTO {

    private Long id;

    @NotBlank(message = "动漫标题不能为空")
    private String title;

    private String description;

    private String coverImage;

    private String category;

    private String author;

    private String director;

    private String studio;

    private Integer episodeCount;

    private String airDate;

    private String endDate;

    private String status;

    private String characters;

    private Double rating;

    private Integer reviewCount;

    private Integer viewCount;

    private Integer favoriteCount;

    private Boolean isFavorited;
}