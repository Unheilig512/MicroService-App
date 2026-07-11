package Profile_Service.Profile_Service.Exceptions.Exceptions_Classes;

import Profile_Service.Profile_Service.DTO.Errors.ErrorCode;

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
