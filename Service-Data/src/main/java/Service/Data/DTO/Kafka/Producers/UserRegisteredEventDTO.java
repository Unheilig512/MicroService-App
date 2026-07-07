package Service.Data.DTO.Kafka.Producers;

import java.util.UUID;

public record UserRegisteredEventDTO(
        UUID userId,
        String username,
        String password
) {}