package de.schulte.springboot.api.model;

import java.time.Instant;

public record User(Long id, String name, String email, Instant dateOfBirth) {
}
