package Service.Data.Services;

import Service.Data.DTO.Errors.ErrorCode;
import Service.Data.DTO.Kafka.Listeners.PasswordUpdateEventDTO;
import Service.Data.DTO.Kafka.Listeners.UsernameUpdateEventDTO;
import Service.Data.Exceptions.ExceptionsClasses.UserDoesntExistException;
import Service.Data.Models.User;
import Service.Data.Repositories.Jpa.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UpdateUserDataService {

    @Autowired
    UserRepository userRepository;

    public void UpdateUsername(UsernameUpdateEventDTO event){
        User user = userRepository.findById(UUID.fromString(event.userId()))
                .orElseThrow(() -> new UserDoesntExistException(ErrorCode.USER_NOT_FOUND));
        user.setUsername(event.username());
        userRepository.save(user);
    }

    public void UpdatePassword(PasswordUpdateEventDTO event){
        User user = userRepository.findById(UUID.fromString(event.userId()))
                .orElseThrow(() -> new UserDoesntExistException(ErrorCode.USER_NOT_FOUND));
        user.setPassword(event.password());
        userRepository.save(user);
    }
}
