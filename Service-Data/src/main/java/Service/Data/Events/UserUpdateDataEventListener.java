package Service.Data.Events;

import Service.Data.DTO.Kafka.Listeners.PasswordUpdateEventDTO;
import Service.Data.DTO.Kafka.Listeners.UsernameUpdateEventDTO;
import Service.Data.Services.UpdateUserDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class UserUpdateDataEventListener {

    @Autowired
    UpdateUserDataService updateUserDataService;

    @Transactional
    @KafkaListener(topics = "user_profile-update-username", groupId = "auth-service-group")
    public void updateUsernameEvent(UsernameUpdateEventDTO event){
        updateUserDataService.UpdateUsername(event);

    }

    @Transactional
    @KafkaListener(topics = "user_profile-update-password", groupId = "auth-service-group")
    public void updatePasswordEvent(PasswordUpdateEventDTO event){
        updateUserDataService.UpdatePassword(event);
    }


}
