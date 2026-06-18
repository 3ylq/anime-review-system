package com.anime.service;

import com.anime.entity.ForumPost;
import com.anime.entity.User;
import com.anime.repository.ForumPostRepository;
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
public class ForumService {

    private final ForumPostRepository forumPostRepository;
    private final UserRepository userRepository;

    public ForumPost createPost(Long userId, ForumPost post) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("用户不存在"));

        post.setUser(user);
        post.setViewCount(0);
        post.setLikeCount(0);
        post.setReplyCount(0);
        post.setIsHot(false);
        post.setIsRecommended(false);
        post.setCreatedAt(LocalDateTime.now());
        post.setUpdatedAt(LocalDateTime.now());

        return forumPostRepository.save(post);
    }

    public Page<ForumPost> getRecommendedPosts(Pageable pageable) {
        return forumPostRepository.findRecommended(pageable);
    }

    public Page<ForumPost> getHotPosts(Pageable pageable) {
        return forumPostRepository.findHotPosts(pageable);
    }

    public Page<ForumPost> getPostsByCategory(String category, Pageable pageable) {
        return forumPostRepository.findByCategory(category, pageable);
    }

    public Page<ForumPost> searchPosts(String keyword, Pageable pageable) {
        return forumPostRepository.findByTitleContaining(keyword, pageable);
    }

    public Page<ForumPost> getUserPosts(Long userId, Pageable pageable) {
        return forumPostRepository.findByUserId(userId, pageable);
    }

    public ForumPost getPostById(Long id) {
        ForumPost post = forumPostRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("帖子不存在"));
        post.setViewCount(post.getViewCount() + 1);
        return forumPostRepository.save(post);
    }

    public void likePost(Long id) {
        ForumPost post = forumPostRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("帖子不存在"));
        post.setLikeCount(post.getLikeCount() + 1);
        forumPostRepository.save(post);
    }

    public void deletePost(Long id) {
        forumPostRepository.deleteById(id);
    }
}