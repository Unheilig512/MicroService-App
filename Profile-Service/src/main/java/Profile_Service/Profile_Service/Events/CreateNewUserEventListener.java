package Profile_Service.Profile_Service.Events;

import Profile_Service.Profile_Service.DTO.Kafka.Listeners.CreateNewUserEventDTO;
import Profile_Service.Profile_Service.Services.CreateProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class CreateNewUserEventListener {

    @Autowired
    CreateProfileService createProfileService;

    @Transactional
    @KafkaListener(topics = "user-registration", groupId = "profile-service-group")
    public void CreateNewUserEvent(CreateNewUserEventDTO event) {
        createProfileService.createProfile(event);
        System.out.println("Received new user registration event");
    }

}
