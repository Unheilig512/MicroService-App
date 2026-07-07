package Service.Data.Exceptions.ExceptionsClasses;

import Service.Data.DTO.Errors.ErrorCode;

public class UserDoesntExistException extends RuntimeException {
    private final ErrorCode errorCode;

    public UserDoesntExistException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
