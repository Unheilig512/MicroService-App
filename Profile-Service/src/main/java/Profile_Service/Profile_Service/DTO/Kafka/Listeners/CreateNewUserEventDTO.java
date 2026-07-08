package Profile_Service.Profile_Service.DTO.Kafka.Listeners;

public record CreateNewUserEventDTO(
        String userId,
        String username,
        String password
) {}
