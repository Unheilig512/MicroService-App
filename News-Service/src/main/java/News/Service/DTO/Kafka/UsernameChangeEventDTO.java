package News.Service.DTO.Kafka;

import java.util.UUID;

public record UsernameChangeEventDTO(
    String oldUsername,
    String newUsername,
    UUID userId
) {}

