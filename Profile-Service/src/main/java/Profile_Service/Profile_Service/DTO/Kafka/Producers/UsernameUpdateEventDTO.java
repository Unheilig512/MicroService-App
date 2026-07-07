package Profile_Service.Profile_Service.DTO.Kafka.Producers;

import java.util.UUID;

public record UsernameUpdateEventDTO(
        UUID userId,
        String username
    ){}
