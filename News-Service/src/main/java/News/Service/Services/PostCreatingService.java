package News.Service.Services;

import News.Service.DTO.PostCreating.PostCreateRequest;
import News.Service.DTO.PostView.PostPreviewResponse;
import News.Service.Models.Category;
import News.Service.Models.Post;
import News.Service.Repositories.CategoryRepository;
import News.Service.Repositories.PostRepository;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class PostCreatingService {

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    PostRepository postRepository;

    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    private static final String REDIS_LATEST_KEY = "news:latest";
    private static final String REDIS_TOTAL_COUNT_KEY = "news:total_count";
    private static final int CACHE_SIZE = 20;



    @Transactional
    public void createNewPost(PostCreateRequest postRequest, Authentication authentication)
    {
        String userIdStr = (String) authentication.getPrincipal();
        UUID userId = UUID.fromString(userIdStr);
        String authorName = authentication.getName();
        String categoryName = postRequest.getCategory().trim().toLowerCase();
        Category category = categoryRepository.findByName(categoryName)
                .orElseGet(() -> categoryRepository.save(new Category(null,categoryName)));
        Post newpost = Post.builder()
                .title(postRequest.getTitle())
                .content(postRequest.getContent())
                .category(category)
                .imageUrl(postRequest.getImageUrl())
                .authorName(authorName)
                .authorId(userId)
                .build();
        postRepository.save(newpost);

        PostPreviewResponse postPreviewResponse = new PostPreviewResponse(
                newpost.getId(),
                newpost.getTitle(),
                newpost.getCategory().getName(),
                newpost.getAuthorName(),
                newpost.getImageUrl(),
                newpost.getCreatedAt()
        );

        redisTemplate.opsForValue().increment(REDIS_TOTAL_COUNT_KEY);
        redisTemplate.opsForList().leftPush(REDIS_LATEST_KEY, postPreviewResponse);
        redisTemplate.opsForList().trim(REDIS_LATEST_KEY, 0, CACHE_SIZE - 1);

        ResponseEntity.ok("Пост успешно создан");
    }
}
