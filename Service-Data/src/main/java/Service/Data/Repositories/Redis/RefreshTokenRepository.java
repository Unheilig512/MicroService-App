package Service.Data.Repositories.Redis;

import Service.Data.Models.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, String> {
        Optional<RefreshToken> findByToken(String userId);
        void deleteByToken(String token);
}
