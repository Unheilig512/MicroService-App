package Profile_Service.Profile_Service.DTO.Errors;

import lombok.Getter;

@Getter
public enum ErrorCode {

    BAD_REQUEST(400, "Некорректный запрос"),
    RESPONSE_BODY_EMPTY(400, "Тело запроса пустое"),

    // 401 Unauthorized
    UNAUTHORIZED(401, "Пользователь не авторизован"),

    // 403 Forbidden
    FORBIDDEN(403, "Доступ запрещен"),

    // 404 Not Found
    USER_NOT_FOUND(404, "Пользователь не найден"),

    UNSUPPORTED_FILE_TYPE(415, "Неподдерживаемый тип файла");


    private final int code;
    private final String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

}