package Service.Data.Events;

import Service.Data.DTO.Kafka.Producers.UserRegisteredEventDTO;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class RegistrationEventProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public RegistrationEventProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendUserRegistrationEvent(UserRegisteredEventDTO event) {
        String topic = "user-registration";
        kafkaTemplate.send(topic, event);
        System.out.println("User registration event sent to Kafka: " + event);

    }

}
