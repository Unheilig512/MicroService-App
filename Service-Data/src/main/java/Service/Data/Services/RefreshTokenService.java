package Service.Data.Services;

import Service.Data.DTO.Enums.ERole;
import Service.Data.DTO.Errors.ErrorCode;
import Service.Data.DTO.JWTResponse;
import Service.Data.Exceptions.ExceptionsClasses.RefreshTokenExpiredException;
import Service.Data.Exceptions.ExceptionsClasses.RefreshTokenNotFoundException;
import Service.Data.Exceptions.ExceptionsClasses.UserDoesntExistException;
import Service.Data.JWT.JWTUtils;
import Service.Data.Models.RefreshToken;
import Service.Data.Models.Role;
import Service.Data.Models.User;
import Service.Data.Repositories.Redis.RefreshTokenRepository;
import Service.Data.Repositories.Jpa.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class RefreshTokenService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RefreshTokenRepository refreshTokenRepository;

    @Autowired
    JWTUtils jwtUtils;

    @Value("${spring.app.jwtRefreshExpirationMs}")
    private long refreshTokenExpiration; // Время жизни токена в секундах

    public RefreshToken findByToken(String token){
        return refreshTokenRepository.findByToken(token)
                .orElseThrow(() -> new RefreshTokenNotFoundException(ErrorCode.REFRESH_TOKEN_NOT_FOUND));

    }

    private String saveNewRefreshToken(UUID userId, String username) {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setUserId(userId);
        refreshToken.setUsername(username);
        refreshToken.setExpiryDate(refreshTokenExpiration);

        refreshTokenRepository.save(refreshToken);
        return refreshToken.getToken();
    }

    @Transactional
    public String createRefreshToken(String username)
            throws UserDoesntExistException{
        User realUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserDoesntExistException(ErrorCode.USER_NOT_FOUND));

        return saveNewRefreshToken(realUser.getUuid(), realUser.getUsername());
    }

    @Transactional
    public void deleteById(String token){
        if(!refreshTokenRepository.existsById(token))
            throw new RefreshTokenNotFoundException(ErrorCode.REFRESH_TOKEN_NOT_FOUND);
        refreshTokenRepository.deleteById(token);
    }

    @Transactional
    public JWTResponse refreshJwtToken(String requestRefreshToken)
            throws RefreshTokenNotFoundException, RefreshTokenExpiredException {

        RefreshToken tokenEntity = refreshTokenRepository.findById(requestRefreshToken)
                .orElseThrow(() -> new RefreshTokenNotFoundException(ErrorCode.REFRESH_TOKEN_NOT_FOUND));

        refreshTokenRepository.delete(tokenEntity);
        User user = userRepository.findById(tokenEntity.getUserId())
                .orElseThrow(() -> new UserDoesntExistException(ErrorCode.USER_NOT_FOUND));

        List<ERole> roles = user.getRoles().stream()
                .map(Role::getName)
                .toList();

        String accessToken = jwtUtils.GeneraterTokenFromUsername(tokenEntity.getUsername(), tokenEntity.getUserId(), roles);
        String newRefreshToken = saveNewRefreshToken(tokenEntity.getUserId(), tokenEntity.getUsername());

        return new JWTResponse(accessToken, newRefreshToken, "Bearer", tokenEntity.getUsername());

    }

}
