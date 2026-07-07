package Profile_Service.Profile_Service.Services;

import Profile_Service.Profile_Service.DTO.Errors.ErrorCode;
import Profile_Service.Profile_Service.Exceptions.Exceptions_Classes.UserNotFoundException;
import Profile_Service.Profile_Service.Models.UserProfile;
import Profile_Service.Profile_Service.Repositories.UserProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ViewProfileService {

    @Autowired
    UserProfileRepository userProfileRepository;

    public UserProfile getUserProfile(UUID userId) {
        if (!userProfileRepository.findById(userId).isPresent())
            throw new UserNotFoundException(ErrorCode.USER_NOT_FOUND);
        return userProfileRepository.findById(userId).get();
    }


}
