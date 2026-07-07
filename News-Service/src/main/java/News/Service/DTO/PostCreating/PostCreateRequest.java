package News.Service.DTO.PostCreating;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@Getter
@Setter
public class PostCreateRequest {

    @NotBlank(message = "Заголовок не может быть пустым")
    @Size(max = 50, message = "Заголовок не может быть длиннее 50 символов")
    private String title;

    @NotBlank(message = "Содержание не может быть пустым")
    @Size(max = 5000, message = "Содержание не может быть длиннее 5000 символов")
    private String content;

    @Size(max = 50, message = "Категория не может быть длиннее 50 символов")
    private String category;

    private String imageUrl;

}
