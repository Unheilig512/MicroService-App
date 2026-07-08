package Service.Data.DTO.Kafka.Listeners;

import java.util.UUID;

public record UsernameUpdateEventDTO(
        String userId,
        String username
    ){}
