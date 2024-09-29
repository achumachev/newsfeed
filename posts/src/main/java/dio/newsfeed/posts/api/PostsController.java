package dio.newsfeed.posts.api;

import dio.newsfeed.posts.dto.CreatePost;
import dio.newsfeed.posts.model.Post;
import dio.newsfeed.posts.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/posts")
public class PostsController {
    private final PostService postService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Post createPost(@RequestBody CreatePost createPost) {
        return postService.cretePost(createPost);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Post> feed() {
        return postService.findPosts();
    }
}
