package Service.Data.Exceptions.ExceptionsClasses;

import Service.Data.DTO.Errors.ErrorCode;

public class RefreshTokenExpiredException extends RuntimeException {
    private final ErrorCode errorCode;

    public RefreshTokenExpiredException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
