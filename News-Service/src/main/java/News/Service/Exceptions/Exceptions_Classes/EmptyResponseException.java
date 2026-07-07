package News.Service.Exceptions.Exceptions_Classes;


import News.Service.DTO.Errors.ErrorCode;

public class EmptyResponseException extends RuntimeException {
    private final ErrorCode errorCode;

    public EmptyResponseException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
