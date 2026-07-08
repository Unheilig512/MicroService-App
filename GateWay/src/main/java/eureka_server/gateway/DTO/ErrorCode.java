package eureka_server.gateway.DTO;


import lombok.Getter;

@Getter
public enum ErrorCode {
    BAD_REQUEST(400, "Некорректный запрос"),
    RESPONSE_BODY_EMPTY(400, "Тело запроса пустое"),

    // 401 Unauthorized
    UNAUTHORIZED(401, "Пользователь не авторизован"),
    INVALID_CREDENTIALS(401, "Неверный логин или пароль"),
    TOKEN_EXPIRED(401, "Срок действия токена истек"),
    REFRESH_TOKEN_NOT_FOUND(401, "Рефреш-токен не найден или недействителен"),
    REFRESH_TOKEN_EXPIRED(401, "Срок действия рефреш-токена истек");

    private final int code;
    private final String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
