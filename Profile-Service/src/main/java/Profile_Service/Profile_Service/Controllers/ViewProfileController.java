package Profile_Service.Profile_Service.Controllers;


import Profile_Service.Profile_Service.Models.UserProfile;
import Profile_Service.Profile_Service.Repositories.UserProfileRepository;
import Profile_Service.Profile_Service.Services.ViewProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/profile")
public class ViewProfileController {

    @Autowired
    UserProfileRepository userProfileRepository;

    @Autowired
    ViewProfileService viewProfileService;

    @GetMapping("/{id}")
    public UserProfile ViewProfile(UUID id) throws Exception {
        return viewProfileService.getUserProfile(id);
    }

}
