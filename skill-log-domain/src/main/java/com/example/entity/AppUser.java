package com.example.entity;

import org.springframework.data.annotation.Id;
import jakarta.persistence.GeneratedValue;

public class AppUser {
    @Id
    @GeneratedValue
    private Long id;

    private String username;
    private String password;
}
