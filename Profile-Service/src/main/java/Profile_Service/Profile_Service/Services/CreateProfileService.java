package Profile_Service.Profile_Service.Services;

import Profile_Service.Profile_Service.DTO.Kafka.Listeners.CreateNewUserEventDTO;
import Profile_Service.Profile_Service.Models.UserProfile;
import Profile_Service.Profile_Service.Repositories.UserProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;


@Service
public class CreateProfileService {

    @Autowired
    UserProfileRepository userProfileRepository;

    @Transactional
    public void createProfile(CreateNewUserEventDTO userDTO){
        UserProfile user = new UserProfile();
        user.setId(UUID.fromString(userDTO.userId()));
        user.setUsername(userDTO.username());
        user.setPassword(userDTO.password());
        userProfileRepository.save(user);

    }
}
