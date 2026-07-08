package Service.Data.Services;

import Service.Data.DTO.ERole;
import Service.Data.DTO.Errors.ErrorCode;
import Service.Data.DTO.Kafka.Producers.UserRegisteredEventDTO;
import Service.Data.Events.RegistrationEventProducer;
import Service.Data.Exceptions.ExceptionsClasses.UserAlreadyExistException;

import Service.Data.Models.Role;
import Service.Data.Models.User;
import Service.Data.Repositories.Jpa.RoleRepository;
import Service.Data.Repositories.Jpa.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    RegistrationEventProducer registrationEventProducer;

    @Transactional
    public void createNewUser(String name, String password)
            throws UserAlreadyExistException {
        if(userRepository.findByUsername(name).isPresent())
            throw new UserAlreadyExistException(ErrorCode.USER_ALREADY_EXISTS);
        User user = new User();
        user.setUsername(name);
        user.setPassword(passwordEncoder.encode(password));

        Role defaultRole = roleRepository.findByName(ERole.ROLE_USER)
                .orElseThrow(() -> new RuntimeException("Роль не найдена"));
        user.getRoles().add(defaultRole);
        userRepository.save(user);

        UserRegisteredEventDTO event =
                new UserRegisteredEventDTO(user.getUuid(), user.getUsername(), user.getPassword());
        registrationEventProducer.sendUserRegistrationEvent(event);
    }




}
