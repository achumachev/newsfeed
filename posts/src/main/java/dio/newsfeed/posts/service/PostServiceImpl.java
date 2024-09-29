package dio.newsfeed.posts.service;

import dio.newsfeed.posts.dto.CreatePost;
import dio.newsfeed.posts.model.Post;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
public class PostServiceImpl implements PostService {

    private static final List<Post> POSTS = new ArrayList<>();

    @Override
    public List<Post> findPosts() {
        return Collections.unmodifiableList(POSTS);
    }

    @Override
    public Post cretePost(CreatePost createPost) {
        Post post = new Post(
                UUID.randomUUID(),
                createPost.name(),
                createPost.author(),
                Instant.now());

        POSTS.add(post);

        return post;
    }

}
