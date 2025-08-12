
package com.example.app.model;

import java.time.LocalDateTime;

public record Message(String name, String email, String body, LocalDateTime createdAt) {
    public Message(String name, String email, String body) {
        this(name, email, body, LocalDateTime.now());
    }
}
