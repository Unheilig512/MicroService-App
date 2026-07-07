package News.Service.Controllers;


import News.Service.DTO.PostView.PostViewResponse;
import News.Service.Services.PostViewService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/posts")
@RequiredArgsConstructor
public class PostsViewController {

    private final PostViewService postViewService;

    @GetMapping
    public ResponseEntity<?> getPosts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
                Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
            return ResponseEntity.ok(postViewService.getPosts(pageable));
    }


    @GetMapping("/{id}")
    public ResponseEntity<PostViewResponse> getNewsById(@PathVariable Long id) {
        return ResponseEntity.ok(postViewService.getPostById(id));
    }

}
