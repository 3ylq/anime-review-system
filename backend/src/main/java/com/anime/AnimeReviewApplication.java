package com.anime;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class AnimeReviewApplication {

    public static void main(String[] args) {
        SpringApplication.run(AnimeReviewApplication.class, args);
        System.out.println("\n========================================");
        System.out.println("   🎬 Anime Review System Started");
        System.out.println("   Server: http://localhost:8080");
        System.out.println("========================================\n");
    }
}