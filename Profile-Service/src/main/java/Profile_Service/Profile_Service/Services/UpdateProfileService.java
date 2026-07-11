package Profile_Service.Profile_Service.Services;

import Profile_Service.Profile_Service.DTO.Errors.ErrorCode;
import Profile_Service.Profile_Service.DTO.Kafka.Producers.PasswordUpdateEventDTO;
import Profile_Service.Profile_Service.DTO.Kafka.Producers.UsernameUpdateEventDTO;
import Profile_Service.Profile_Service.DTO.Requests.ProfileChangeRequest;
import Profile_Service.Profile_Service.Events.UpdateDataEventProducer;
import Profile_Service.Profile_Service.Exceptions.Exceptions_Classes.UserNotFoundException;
import Profile_Service.Profile_Service.Models.UserProfile;
import Profile_Service.Profile_Service.Repositories.UserProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.regex.Pattern;

@Service
public class UpdateProfileService {

    @Autowired
    UserProfileRepository userProfileRepository;

    @Autowired
    UpdateDataEventProducer updateDataEventProducer;

    private static final Pattern USERNAME_PATTERN = Pattern.compile("^[a-zA-Z0-9_-]{3,32}$");

    @Transactional
    public void updateProfile(ProfileChangeRequest profileChangeRequest){
        UserProfile userProfile = userProfileRepository.findById(profileChangeRequest.getUserId())
                .orElseThrow(() -> new UserNotFoundException(ErrorCode.USER_NOT_FOUND));

        if(profileChangeRequest.getUsername() != null){
            if(!USERNAME_PATTERN.matcher(profileChangeRequest.getUsername()).matches())
                throw new IllegalArgumentException("Invalid username format. Username can only contain letters, numbers, underscores, and hyphens.");
            updateProfileUsername(userProfile, profileChangeRequest.getUsername());
        }
        if(profileChangeRequest.getPassword() != null){
            updateProfilePassword(userProfile, profileChangeRequest.getPassword());
        }
        if(profileChangeRequest.getDescription() != null){
            updateProfileDescription(userProfile, profileChangeRequest.getDescription());
        }
        if(profileChangeRequest.getImageUrl() != null){
            updateProfilePicture(userProfile, profileChangeRequest.getImageUrl());
        }
    }

    @Transactional
    public void updateProfileUsername(UserProfile userProfile, String newUsername) {
        userProfile.setUsername(newUsername);
        userProfileRepository.save(userProfile);

        UsernameUpdateEventDTO event = new UsernameUpdateEventDTO(userProfile.getId(), newUsername);
        updateDataEventProducer.sendUpdateUsernameEvent(event);
    }

    @Transactional
    public void updateProfilePassword(UserProfile userProfile, String newPassword) {
        userProfile.setPassword(newPassword);
        userProfileRepository.save(userProfile);

        PasswordUpdateEventDTO event = new PasswordUpdateEventDTO(userProfile.getId(), newPassword);
        updateDataEventProducer.sendUpdatePasswordEvent(event);
    }

    @Transactional
    public void updateProfileDescription(UserProfile userProfile, String newDescription) {
        userProfile.setDescription(newDescription);
        userProfileRepository.save(userProfile);
    }

    @Transactional
    public void updateProfilePicture(UserProfile userProfile, String newProfilePicture) {
        userProfile.setImageUrl(newProfilePicture);
        userProfileRepository.save(userProfile);
    }



}
