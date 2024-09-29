package dio.newsfeed.posts.api;

import dio.newsfeed.posts.dto.CreatePost;
import dio.newsfeed.posts.model.Post;
import dio.newsfeed.posts.service.PostService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class PostsControllerTest {
    @MockBean
    private PostService postService;
    @Autowired
    private MockMvc mockMvc;

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(postService);
    }

    @Test
    void testCreatePostOk() throws Exception {
        UUID id = UUID.randomUUID();
        Instant now = Instant.now();

        when(postService.cretePost(any())).then(ic -> {
            CreatePost cp = ic.getArgument(0);
            return new Post(id, cp.name(), cp.author(), now);
        });

        mockMvc.perform(post("/api/v1/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                            {
                                "name": "Super post",
                                "author": "someone"
                            }"""))
                .andExpectAll(
                        status().isCreated(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        content().json("""
                                {
                                    "name": "Super post",
                                    "author": "someone",
                                    "creationDate": "%s",
                                    "id": "%s"

                                }""".formatted(now, id)
                        )
                );

        ArgumentCaptor<CreatePost> captor = ArgumentCaptor.forClass(CreatePost.class);
        verify(postService).cretePost(captor.capture());
        assertEquals("Super post", captor.getValue().name());
        assertEquals("someone", captor.getValue().author());
    }

    @Test
    void testFeedOk() throws Exception {
        UUID id1 = UUID.randomUUID();
        Instant date1 = Instant.now();
        UUID id2 = UUID.randomUUID();
        Instant date2 = Instant.now();

        when(postService.findPosts()).thenReturn(List.of(
                new Post(id1, "first post", "someone", date1),
                new Post(id2, "second post", "elseone", date2)
        ));

        mockMvc.perform(get("/api/v1/posts"))
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        content().json("""
                                [
                                    {
                                        "name": "first post",
                                        "author": "someone",
                                        "creationDate": "%s",
                                        "id": "%s"
                                    },
                                    {
                                        "name": "second post",
                                        "author": "elseone",
                                        "creationDate": "%s",
                                        "id": "%s"
                                    }
                                 ]""".formatted(date1, id1, date2, id2)
                        )
                );

        verify(postService).findPosts();
    }

    @Test
    void testFeedEmptyResponse() throws Exception {
        when(postService.findPosts()).thenReturn(List.of());

        mockMvc.perform(get("/api/v1/posts"))
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        content().json("[]")
                );

        verify(postService).findPosts();
    }
}