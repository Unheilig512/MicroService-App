package Profile_Service.Profile_Service.DTO.Requests;


import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@Getter
@Setter
public class ProfileChangeRequest {

    UUID userId;

    @Size(min = 3, max = 50, message = "Логин должен быть от 3 до 50 символов")
    String username;
    String password;
    String description;
    String imageUrl;
}
