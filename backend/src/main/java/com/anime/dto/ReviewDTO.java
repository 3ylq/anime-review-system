package com.anime.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDTO {

    private Long id;

    @NotNull(message = "动漫ID不能为空")
    private Long animeId;

    private String content;

    @NotNull(message = "评分不能为空")
    private Double rating;

    private Integer likeCount;

    private Integer replyCount;

    private UserDTO user;

    private String createdAt;
}