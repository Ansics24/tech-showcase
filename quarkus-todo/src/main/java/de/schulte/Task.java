package de.schulte;

import java.time.Instant;

public record Task (Long id, String title, String description, Instant due){
}
