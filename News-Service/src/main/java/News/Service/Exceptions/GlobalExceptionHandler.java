package News.Service.Exceptions;

import News.Service.DTO.Errors.ErrorMessage;
import News.Service.Exceptions.Exceptions_Classes.EmptyResponseException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(EmptyResponseException.class)
    public ResponseEntity<ErrorMessage> handleEmptyResponseException(EmptyResponseException ex) {
        ErrorMessage errorMessage = new ErrorMessage(ex.getErrorCode());
        return ResponseEntity
                .status(ex.getErrorCode().getCode())
                .body(errorMessage);
    }
}
