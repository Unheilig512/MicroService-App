package Profile_Service.Profile_Service.Controllers;

import Profile_Service.Profile_Service.DTO.Errors.ErrorCode;
import Profile_Service.Profile_Service.DTO.Requests.ProfileChangeRequest;
import Profile_Service.Profile_Service.Exceptions.Exceptions_Classes.UserNotFoundException;
import Profile_Service.Profile_Service.Services.UpdateProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequestMapping("/api/profile")
@RestController
public class UpdateProfileController {

    @Autowired
    UpdateProfileService updateProfileService;

    @PostMapping("/change/{id}")
    public ResponseEntity<?> changeProfile(
            @RequestBody ProfileChangeRequest request,
            @RequestHeader("X-User-Id") String userId) {
        if(request == null)
            throw new UserNotFoundException(ErrorCode.RESPONSE_BODY_EMPTY);
        if(!UUID.fromString(userId).equals(request.getUserId()))
            return ResponseEntity.status(403).body("User ID is invalid");
        updateProfileService.updateProfile(request);
        return ResponseEntity.ok("Profile updated successfully");

    }
}


