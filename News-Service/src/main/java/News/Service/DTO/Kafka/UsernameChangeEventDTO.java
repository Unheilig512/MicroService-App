package News.Service.DTO.Kafka;

import java.util.UUID;

public record UsernameChangeEventDTO(
        String userId,
        String oldUsername,
        String newUsername
) {}

