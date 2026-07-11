package News.Service.Exceptions;

import News.Service.DTO.Errors.ErrorMessage;
import News.Service.Exceptions.Exceptions_Classes.EmptyResponseException;
import News.Service.Exceptions.Exceptions_Classes.FileValidationException;
import News.Service.Exceptions.Exceptions_Classes.FileWrongTypeException;
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

    @ExceptionHandler(FileValidationException.class)
    public ResponseEntity<ErrorMessage> handleFileValidationException(FileValidationException ex) {
        ErrorMessage errorMessage = new ErrorMessage(ex.getErrorCode());
        return ResponseEntity
                .status(ex.getErrorCode().getCode())
                .body(errorMessage);
    }

    @ExceptionHandler(FileWrongTypeException.class)
    public ResponseEntity<ErrorMessage> handleFileWrongTypeException(FileWrongTypeException ex) {
        ErrorMessage errorMessage = new ErrorMessage(ex.getErrorCode());
        return ResponseEntity
                .status(ex.getErrorCode().getCode())
                .body(errorMessage);
    }

}
