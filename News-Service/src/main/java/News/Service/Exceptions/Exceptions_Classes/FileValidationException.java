package News.Service.Exceptions.Exceptions_Classes;

import News.Service.DTO.Errors.ErrorCode;

public class FileValidationException extends RuntimeException {
    private final ErrorCode errorCode;

    public FileValidationException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
