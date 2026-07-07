package Profile_Service.Profile_Service.Repositories;

import Profile_Service.Profile_Service.Models.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserProfileRepository extends JpaRepository<UserProfile, UUID> {
    Optional<UserProfile> findById(UUID id);
}
