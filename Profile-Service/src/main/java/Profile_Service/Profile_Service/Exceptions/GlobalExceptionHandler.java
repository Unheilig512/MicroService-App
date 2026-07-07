package Profile_Service.Profile_Service.Exceptions;

import Profile_Service.Profile_Service.DTO.Errors.ErrorMessage;
import Profile_Service.Profile_Service.Exceptions.Exceptions_Classes.EmptyResponseException;
import Profile_Service.Profile_Service.Exceptions.Exceptions_Classes.UserNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorMessage> handleUserNotFoundException(UserNotFoundException ex) {
        ErrorMessage errorMessage = new ErrorMessage(ex.getErrorCode());
        return ResponseEntity
                .status(ex.getErrorCode().getCode())
                .body(errorMessage);
    }

    @ExceptionHandler(EmptyResponseException.class)
    public ResponseEntity<ErrorMessage> handleEmptyResponseException(EmptyResponseException ex) {
        ErrorMessage errorMessage = new ErrorMessage(ex.getErrorCode());
        return ResponseEntity
                .status(ex.getErrorCode().getCode())
                .body(errorMessage);
    }

}
