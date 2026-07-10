package Service.Data.Controllers;


import Service.Data.DTO.Errors.ErrorCode;
import Service.Data.DTO.JWTResponse;
import Service.Data.DTO.Requests.LoginRequest;
import Service.Data.DTO.Requests.RefreshTokenRequest;
import Service.Data.Exceptions.ExceptionsClasses.EmptyResponseException;
import Service.Data.JWT.JWTUtils;
import Service.Data.Repositories.Jpa.UserRepository;
import Service.Data.Services.RefreshTokenService;
import Service.Data.Services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    UserService userService;

    @Autowired
    RefreshTokenService refreshTokenService;

    @PostMapping("/create")
    public ResponseEntity<?> createUser(@Valid @RequestBody LoginRequest request) throws Exception {
        if(request == null || request.username() == null || request.password() == null){
            throw new EmptyResponseException(ErrorCode.RESPONSE_BODY_EMPTY);
        }
        userService.createNewUser(request.username(), request.password());
        return ResponseEntity.ok("Пользователь успешно создан");
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@Valid @RequestBody LoginRequest loginRequest) throws Exception {
        if (loginRequest == null || loginRequest.username() == null || loginRequest.password() == null)
            throw new EmptyResponseException(ErrorCode.RESPONSE_BODY_EMPTY);

        userService.loginUser(loginRequest);
        return ResponseEntity.ok("Пользователь успешно вошел в систему");
    }

    @PostMapping("/refresh")
    public JWTResponse refreshToken(@RequestBody RefreshTokenRequest requestRefreshToken){
        return refreshTokenService.refreshJwtToken(requestRefreshToken.refreshToken());
    }
}
