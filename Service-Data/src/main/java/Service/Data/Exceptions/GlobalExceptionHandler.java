package Service.Data.Exceptions;


import Service.Data.DTO.Errors.ErrorCode;
import Service.Data.DTO.Errors.ErrorMessage;
import Service.Data.Exceptions.ExceptionsClasses.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserAlreadyExistException.class)
    public ResponseEntity<ErrorMessage> handleUserAlreadyExist(UserAlreadyExistException ex) {
        ErrorCode code = ex.getErrorCode();
        return ResponseEntity
                .status(code.getCode())
                .body(new ErrorMessage(code));
    }

    @ExceptionHandler(UserDoesntExistException.class)
    public ResponseEntity<ErrorMessage> handleUserDoesntExist(UserDoesntExistException ex){
        ErrorCode code = ex.getErrorCode();
        return ResponseEntity
                .status(code.getCode())
                .body(new ErrorMessage(code));
    }

    @ExceptionHandler(UserLoginRequestDataWrongException.class)
    public ResponseEntity<ErrorMessage> handleUserDoesntExist(UserLoginRequestDataWrongException ex){
        ErrorCode code = ex.getErrorCode();
        return ResponseEntity
                .status(code.getCode())
                .body(new ErrorMessage(code));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessage> handleValidationException(MethodArgumentNotValidException ex){
        ErrorCode code = ErrorCode.INVALID_CREDENTIALS;
        String error = ex.getBindingResult()
                .getFieldErrors()
                .get(0)
                .getDefaultMessage();
        return ResponseEntity.badRequest().body(new ErrorMessage("Ошибка ввода данных", error, code.getCode()));

    }

    @ExceptionHandler(JwtAuthenticationException.class)
    public ResponseEntity<ErrorMessage> handleJwtAuthenticationException(JwtAuthenticationException ex){
        ErrorCode code = ex.getErrorCode();
        return ResponseEntity
                .status(code.getCode())
                .body(new ErrorMessage(code));

    }

    @ExceptionHandler(RefreshTokenNotFoundException.class)
    public ResponseEntity<ErrorMessage> handleRefreshTokenNotFound(RefreshTokenNotFoundException ex){
        ErrorCode code = ex.getErrorCode();
        return ResponseEntity
                .status(code.getCode())
                .body(new ErrorMessage(code));
    }

    @ExceptionHandler(RefreshTokenExpiredException.class)
    public ResponseEntity<ErrorMessage> handleRefreshTokenUnsupported(RefreshTokenExpiredException ex){
        ErrorCode code = ex.getErrorCode();
        return ResponseEntity
                .status(code.getCode())
                .body(new ErrorMessage(code));
    }
}
