package dio.newsfeed.posts.model;

import java.time.Instant;
import java.util.UUID;

public record Post(
        UUID id,
        String name,
        String author,
        Instant creationDate) {
}
