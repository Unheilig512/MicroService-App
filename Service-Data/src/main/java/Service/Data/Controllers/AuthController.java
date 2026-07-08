package Service.Data.Controllers;


import Service.Data.DTO.ERole;
import Service.Data.DTO.Errors.ErrorCode;
import Service.Data.DTO.JWTResponse;
import Service.Data.DTO.LoginRequest;
import Service.Data.Exceptions.ExceptionsClasses.EmptyResponseException;
import Service.Data.JWT.JWTUtils;
import Service.Data.Models.Role;
import Service.Data.Models.User;
import Service.Data.Repositories.Jpa.UserRepository;
import Service.Data.Services.RefreshTokenService;
import Service.Data.Services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    UserService userService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JWTUtils jwtUtils;

    @Autowired
    RefreshTokenService refreshTokenService;

    @Autowired
    UserRepository userRepository;

    @PostMapping("/create")
    public ResponseEntity<?> createUser(@Valid @RequestBody LoginRequest request) throws Exception {
        if(request == null || request.getUsername() == null || request.getPassword() == null){
            throw new EmptyResponseException(ErrorCode.RESPONSE_BODY_EMPTY);
        }
        userService.createNewUser(request.getUsername(), request.getPassword());
        return ResponseEntity.ok("Пользователь успешно создан");
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@Valid @RequestBody LoginRequest loginRequest) throws Exception {
        if(loginRequest == null || loginRequest.getUsername() == null || loginRequest.getPassword() == null){
            throw new EmptyResponseException(ErrorCode.RESPONSE_BODY_EMPTY);
        }
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );
        User user = userRepository.findByUsername(loginRequest.getUsername()).get();

        List<ERole> roles = user.getRoles().stream()
                .map(Role::getName)
                .toList();
        String jwt = jwtUtils.GeneraterTokenFromUsername(loginRequest.getUsername(), user.getUuid(), roles);
        String refreshToken = refreshTokenService.createRefreshToken(loginRequest.getUsername());

        return ResponseEntity.ok(new JWTResponse(jwt, refreshToken, "Bearer", loginRequest.getUsername()));
    }

    @PostMapping("/refresh")
    public JWTResponse refreshToken(@RequestBody String requestRefreshToken){
        return refreshTokenService.refreshJwtToken(requestRefreshToken);
    }
}
