package de.schulte.quarkus.api.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;

import java.time.Instant;

public record User(@NotNull Long id, @NotNull String name, @Email String email, @Past Instant dateOfBirth) {
}
