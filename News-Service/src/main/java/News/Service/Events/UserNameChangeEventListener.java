package News.Service.Events;

import News.Service.DTO.Kafka.UsernameChangeEventDTO;
import News.Service.Repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Component
public class UserNameChangeEventListener {

    @Autowired
    PostRepository postRepository;

    @Transactional
    @KafkaListener(topics = "user_profile-update-username", groupId = "posts-profile-group")
    public void handleUserNameChangeEvent(UsernameChangeEventDTO event) {
        UUID authorId = event.userId();
        int count = postRepository.updateAuthorName(authorId, event.newUsername());

        System.out.println("Updated author name for " + count + " posts for user: " + event.newUsername());
    }
}
