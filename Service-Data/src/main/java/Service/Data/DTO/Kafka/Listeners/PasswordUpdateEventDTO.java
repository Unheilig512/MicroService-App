package Service.Data.DTO.Kafka.Listeners;

import java.util.UUID;

public record PasswordUpdateEventDTO(
    String userId,
    String password
) {}
