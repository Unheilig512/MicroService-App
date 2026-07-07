package Profile_Service.Profile_Service.Events;

import Profile_Service.Profile_Service.DTO.Kafka.Producers.PasswordUpdateEventDTO;
import Profile_Service.Profile_Service.DTO.Kafka.Producers.UsernameUpdateEventDTO;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class UpdateDataEventProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public UpdateDataEventProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendUpdateUsernameEvent(UsernameUpdateEventDTO event){
        String topic = "user_profile-update-username";
        kafkaTemplate.send(topic, event);
        System.out.println("User update username event sent to Kafka: " + event);
    }

    public void sendUpdatePasswordEvent(PasswordUpdateEventDTO event){
        String topic = "user_profile-update-password";
        kafkaTemplate.send(topic, event);
        System.out.println("User update password event sent to Kafka: " + event);
    }


}
