package eureka_server.gateway.Exceptions;

import Service.Data.DTO.Errors.ErrorCode;
import Service.Data.DTO.Errors.ErrorMessage;
import Service.Data.Exceptions.ExceptionsClasses.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;



@RestControllerAdvice
public class GlobalExceptionHandler {
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

}
