package eureka_server.gateway.Exceptions.Exception_Classes;

import org.springframework.security.core.AuthenticationException;
import eureka_server.gateway.DTO.ErrorCode;

public class JwtAuthenticationException extends AuthenticationException{
    private final ErrorCode errorCode;

    public JwtAuthenticationException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
