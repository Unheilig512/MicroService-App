package Profile_Service.Profile_Service.Controllers;

import Profile_Service.Profile_Service.DTO.Errors.ErrorCode;
import Profile_Service.Profile_Service.DTO.Requests.ProfileChangeRequest;
import Profile_Service.Profile_Service.Exceptions.Exceptions_Classes.UserNotFoundException;
import Profile_Service.Profile_Service.Services.UpdateProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/profile")
@RestController
public class UpdateProfileController {

    @Autowired
    UpdateProfileService updateProfileService;

    @PostMapping("/change")
    public ResponseEntity<?> changeProfile(@RequestBody ProfileChangeRequest request) throws Exception {
        if(request == null)
            throw new UserNotFoundException(ErrorCode.RESPONSE_BODY_EMPTY);
        updateProfileService.updateProfile(request);
        return ResponseEntity.ok("Profile updated successfully");

    }
}


