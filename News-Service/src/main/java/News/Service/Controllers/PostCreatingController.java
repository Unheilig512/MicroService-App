package News.Service.Controllers;

import News.Service.DTO.PostCreating.PostCreateRequest;
import News.Service.Services.PostCreatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/postcreate")
public class PostCreatingController {

    @Autowired
    PostCreatingService postCreatingService;

    @PostMapping("create")
    public ResponseEntity<String> createPost(@RequestBody PostCreateRequest newPost, Authentication authentication) {
        postCreatingService.createNewPost(newPost, authentication);
        return ResponseEntity.ok("Post created successfully");
    }

}
