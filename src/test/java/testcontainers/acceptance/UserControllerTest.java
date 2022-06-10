package testcontainers.acceptance;

import com.emlakjet.testcontainers.TestcontainersDemoApplication;
import com.emlakjet.testcontainers.dto.UserDto;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.*;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@Testcontainers
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = TestcontainersDemoApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class UserControllerTest {

    @Container
    private static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:latest");

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper mapper = new ObjectMapper();

    @DynamicPropertySource
    static void overrideProps(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Test
    @DisplayName("create user via endpoint")
    void should_create_user() throws Exception {
        var body = mapper.writeValueAsString(new UserDto(0L, "Emre", "Eren", "remreren"));
        var request = post("/user/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body);

        var mvcResult = mockMvc.perform(request)
                .andDo(print())
                .andReturn();

        var content = mvcResult.getResponse().getContentAsString();
        var result = mapper.readValue(content, UserDto.class);

        assertEquals(200, mvcResult.getResponse().getStatus());
        assertEquals("Emre", result.getName());
        assertEquals("Eren", result.getSurname());
        assertEquals("remreren", result.getUsername());
    }
}