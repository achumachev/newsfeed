package dio.newsfeed.posts.service;

import dio.newsfeed.posts.dto.CreatePost;
import dio.newsfeed.posts.model.Post;

import java.util.List;

public interface PostService {

    List<Post> findPosts();

    Post cretePost(CreatePost createPost);
}
