package Profile_Service.Profile_Service.DTO.Kafka.Listeners;

import java.util.UUID;

public record CreateNewUserEventDTO(
        UUID userId,
        String username,
        String password
) {}
