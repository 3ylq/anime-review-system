package com.anime.controller;

import com.anime.dto.UserDTO;
import com.anime.entity.User;
import com.anime.security.JwtTokenProvider;
import com.anime.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AuthController {

    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> register(@Valid @RequestBody UserDTO userDTO) {
        try {
            User user = userService.register(userDTO);
            String token = jwtTokenProvider.generateToken(user.getId());

            Map<String, Object> response = new HashMap<>();
            response.put("message", "注册成功");
            response.put("token", token);
            response.put("user", mapUserToDTO(user));

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, String> loginRequest) {
        try {
            String usernameOrEmail = loginRequest.get("usernameOrEmail");
            String password = loginRequest.get("password");

            User user = userService.login(usernameOrEmail, password);
            String token = jwtTokenProvider.generateToken(user.getId());

            Map<String, Object> response = new HashMap<>();
            response.put("message", "登录成功");
            response.put("token", token);
            response.put("user", mapUserToDTO(user));

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
        }
    }

    @PostMapping("/reset-password")
    public ResponseEntity<Map<String, Object>> resetPassword(@RequestBody Map<String, String> request) {
        try {
            String email = request.get("email");
            String newPassword = request.get("newPassword");

            userService.resetPassword(email, newPassword);

            Map<String, Object> response = new HashMap<>();
            response.put("message", "密码重置成功");

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }

    @PostMapping("/change-password")
    public ResponseEntity<Map<String, Object>> changePassword(
            @RequestHeader("Authorization") String token,
            @RequestBody Map<String, String> request) {
        try {
            Long userId = jwtTokenProvider.getUserIdFromToken(token);
            String oldPassword = request.get("oldPassword");
            String newPassword = request.get("newPassword");

            userService.changePassword(userId, oldPassword, newPassword);

            Map<String, Object> response = new HashMap<>();
            response.put("message", "密码修改成功");

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }

    private UserDTO mapUserToDTO(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .nickname(user.getNickname())
                .avatar(user.getAvatar())
                .bio(user.getBio())
                .followers(user.getFollowers())
                .following(user.getFollowing())
                .build();
    }
}