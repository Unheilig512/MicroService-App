package gateway.Utils;

import Service.Data.DTO.ERole;
import Service.Data.DTO.ErrorCode;
import Service.Data.Exceptions.ExceptionsClasses.JwtAuthenticationException;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;
import java.util.List;


@Component
public class JWTUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(JWTUtils.class);

    @Value("${spring.app.jwtSecret}")
        private String jwtSecret;
    @Value("${spring.app.jwtExpirationMs}")
        private int jwtExpirationMs;

    public String GetJwtFromHeader(HttpServletRequest request){
        String bearerToken = request.getHeader("Authorization");
        LOGGER.debug("Authorization header: {}", bearerToken);
        if(bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public String GeneraterTokenFromUsername(String username, Long id, List<ERole> roles) {
        return Jwts.builder()
                .setSubject(username)
                .claim("id", id)           // Добавили ID
                .claim("roles", roles)     // Добавили Роли
                .setIssuedAt(new java.util.Date())
                .setExpiration(new java.util.Date((new java.util.Date()).getTime() + jwtExpirationMs))
                .signWith(key())
                .compact();
    }

    public long getRemainingTime(String token) {
        try {
            Date expiration = Jwts.parser()
                    .setSigningKey(jwtSecret)
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getExpiration();
            long remainingTime = expiration.getTime() - System.currentTimeMillis();
            return remainingTime > 0 ? remainingTime : 0;
        }
        catch (JwtException e) {
            LOGGER.error("Error parsing JWT token: {}", e.getMessage());
            return 0;
        }
    }

    private Key key(){
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    public boolean validateJwtToken(String authToken){
        try{
            Jwts.parser().verifyWith((SecretKey) key()).build().parseClaimsJws(authToken);
            return true;
        } catch (ExpiredJwtException e) {
            throw new JwtAuthenticationException(ErrorCode.TOKEN_EXPIRED);
        } catch (UnsupportedJwtException e) {
            throw new JwtAuthenticationException(ErrorCode.UNAUTHORIZED);
        } catch (MalformedJwtException e) {
            throw new JwtAuthenticationException(ErrorCode.UNAUTHORIZED);
        } catch (SignatureException e) {
            throw new JwtAuthenticationException(ErrorCode.BAD_REQUEST);
        } catch (IllegalArgumentException e) {
            throw new JwtAuthenticationException(ErrorCode.UNAUTHORIZED);
        }
    }


}
