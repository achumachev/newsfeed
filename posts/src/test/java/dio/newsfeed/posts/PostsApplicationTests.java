package dio.newsfeed.posts;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class PostsApplicationTests {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void testContextLoads() throws Exception {
        mockMvc.perform(get("/actuator/health"))
                .andDo(log())
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.status").value("UP")
                );
    }

}
