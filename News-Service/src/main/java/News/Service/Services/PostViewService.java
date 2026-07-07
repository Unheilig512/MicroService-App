package News.Service.Services;

import News.Service.DTO.PostView.PostViewResponse;
import News.Service.DTO.PostView.PostPreviewResponse;
import News.Service.Models.Post;
import News.Service.Repositories.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.PageImpl;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostViewService {

    private final PostRepository postRepository;

    private final RedisTemplate<String, Object> redisTemplate;

    @Transactional(readOnly = true)
    public Page<PostPreviewResponse> getPosts(Pageable pageable){
          if(pageable.getPageNumber() == 0) {
              List<Object> cachedNews = redisTemplate.opsForList().range("news:latest", 0, -1);
              if (cachedNews != null && cachedNews.size() == pageable.getPageSize()) {
                  Integer totalCount = (Integer) redisTemplate.opsForValue().get("news:total_count");
                  if (totalCount == null) {
                      totalCount = cachedNews.size();
                  }

                  List<PostPreviewResponse> responses = cachedNews.stream()
                          .map(obj -> (PostPreviewResponse) obj)
                          .toList();

                  return new PageImpl<>(responses, pageable, totalCount);
              }
          }
          //return postRepository.findAllPostsPreview(pageable);
          Page<PostPreviewResponse> dbPage = postRepository.findAllPostsPreview(pageable);
          if(pageable.getPageNumber() == 0){
              redisTemplate.delete("news:latest");
              redisTemplate.opsForList().rightPushAll("news:latest", dbPage.getContent().toArray());
                redisTemplate.opsForValue().set("news:total_count", dbPage.getTotalElements());
          }
          return dbPage;

    }

    @Transactional(readOnly = true)
    public PostViewResponse getPostById(Long id){
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found with id: " + id));

        return mapToFullResponse(post);
    }

    private PostViewResponse mapToFullResponse(Post post) {
        return PostViewResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .imageUrl(post.getImageUrl())
                .authorName(post.getAuthorName())
                .createdAt(post.getCreatedAt())
                // Извлекаем имя из связанной сущности Category
                .category(post.getCategory() != null ? post.getCategory().getName() : "Без категории")
                .build();
    }

}
