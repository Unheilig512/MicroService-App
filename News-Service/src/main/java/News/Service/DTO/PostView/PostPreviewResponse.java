package News.Service.DTO.PostView;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class PostPreviewResponse
{
    private Long id;
    private String title;
    private String category;
    private String authorName;
    private String imageUrl;
    private LocalDateTime createdAt;

}
