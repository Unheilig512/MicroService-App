package gateway.Utils;

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
import java.security.SignatureException;
import java.util.Date;
import java.util.UUID;


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

    private Claims extractAllClaims(String token){
        return Jwts.parser()
                .verifyWith(key())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public long getRemainingTime(String token) {
        try {
            Date expiration = extractAllClaims(token).getExpiration();
            long remainingTime = expiration.getTime() - System.currentTimeMillis();
            return remainingTime > 0 ? remainingTime : 0;
        }
        catch (JwtException e) {
            LOGGER.error("Error parsing JWT token: {}", e.getMessage());
            return 0;
        }
    }

    public UUID getUserIdFromToken(String token){
        Claims claims = extractAllClaims(token);
        return claims.get("id", UUID.class);
    }

    public String getUsernameFromToken(String token){
        Claims claims = extractAllClaims(token);
        return claims.getSubject();
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

    private SecretKey key(){
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);
        return Keys.hmacShaKeyFor(keyBytes);
    }



}
