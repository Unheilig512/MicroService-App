package News.Service.DTO.PostView;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
public class PostViewResponse {

    private Long id;
    private String title;
    private String content;
    private String imageUrl;
    private String authorName;
    private String category;
    private LocalDateTime createdAt;
}
