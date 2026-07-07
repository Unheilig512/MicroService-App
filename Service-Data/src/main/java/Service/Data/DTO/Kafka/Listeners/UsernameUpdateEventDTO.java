package Service.Data.DTO.Kafka.Listeners;

import java.util.UUID;

public record UsernameUpdateEventDTO(
        UUID userId,
        String username
    ){}
