package News.Service.DTO.Errors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ErrorMessage {
    private int statusCode;
    private String error;
    private String message;

    public ErrorMessage(ErrorCode status) {
        this.statusCode = status.getCode();
        this.error = status.getMessage();
        this.message = status.getMessage();
    }

    public ErrorMessage(String message, String error, int statusCode) {
        this.statusCode = statusCode;
        this.error = error;
        this.message = error;
    }


}
