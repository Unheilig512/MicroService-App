package News.Service.Exceptions.Exceptions_Classes;

import News.Service.DTO.Errors.ErrorCode;

public class FileWrongTypeException extends RuntimeException {
    private final ErrorCode errorCode;

    public FileWrongTypeException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}