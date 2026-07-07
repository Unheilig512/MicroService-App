package Service.Data.Models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.data.redis.core.index.Indexed;

import java.util.UUID;


@Getter
@Setter
@RedisHash(value = "RefreshToken")
public class RefreshToken {
    @Id
    private String token;

    @Indexed
    private UUID userId;

    private String username;

    @TimeToLive
    private Long expiryDate;
}