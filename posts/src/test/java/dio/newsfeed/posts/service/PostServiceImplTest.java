package dio.newsfeed.posts.service;

import dio.newsfeed.posts.dto.CreatePost;
import dio.newsfeed.posts.model.Post;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PostServiceImplTest {
    private PostService postService;

    @BeforeEach
    void setUp() {
        postService = new PostServiceImpl();
    }

    @Test
    void cretePostOk() {
        CreatePost cp = new CreatePost("post1", "author1");
        Post post = postService.cretePost(cp);
        assertAll(() -> {
            assertEquals(cp.name(), post.name());
            assertEquals(cp.author(), post.author());
            assertNotNull(post.id());
            assertNotNull(post.creationDate());
        });
    }

    @Test
    void findAllOk() {
        assertTrue(postService.findPosts().isEmpty());

        postService.cretePost(new CreatePost("post1", "author1"));
        postService.cretePost(new CreatePost("post2", "author2"));

        List<Post> posts = postService.findPosts();
        assertAll(() -> {
            assertEquals(2, posts.size());

            assertEquals("post1", posts.get(0).name());
            assertEquals("author1", posts.get(0).author());

            assertEquals("post2", posts.get(1).name());
            assertEquals("author2", posts.get(1).author());

            posts.forEach(post -> {
                assertNotNull(post.id());
                assertNotNull(post.creationDate());
            });
        });
    }
}