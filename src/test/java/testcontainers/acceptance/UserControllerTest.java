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
import org.springframework.test.web.servlet.MvcResult;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.*;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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

        mockMvc.perform(request)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Emre"))
                .andExpect(jsonPath("$.surname").value("Eren"))
                .andExpect(jsonPath("$.username").value("remreren"))
                .andReturn();
    }

    @Test
    @DisplayName("get user by id")
    void should_get_user_by_id() throws Exception {
        var createBody = mapper.writeValueAsString(new UserDto(0L, "Emre", "Eren", "remreren"));
        var requestCreate = post("/user/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(createBody);
        var createResponse = mockMvc.perform(requestCreate).andReturn();
        var id = toUserDto(createResponse).getId();
        var requestGet = get(String.format("/user/%d/", id));

        mockMvc.perform(requestGet)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id.toString()))
                .andExpect(jsonPath("$.name").value("Emre"))
                .andExpect(jsonPath("$.surname").value("Eren"))
                .andExpect(jsonPath("$.username").value("remreren"))
                .andReturn();
    }

    @Test
    @DisplayName("get user detail by id")
    void should_get_user_detail_by_id() throws Exception {
        var createBody = mapper.writeValueAsString(new UserDto(0L, "Emre", "Eren", "remreren"));
        var requestCreate = post("/user/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(createBody);
        var createResponse = mockMvc.perform(requestCreate).andReturn();
        var id = toUserDto(createResponse).getId();
        var requestGet = get(String.format("/user/%d/detail/", id));

        mockMvc.perform(requestGet)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id.toString()))
                .andExpect(jsonPath("$.name").value("Emre"))
                .andExpect(jsonPath("$.surname").value("Eren"))
                .andExpect(jsonPath("$.username").value("remreren"))
                .andExpect(jsonPath("$.createdAt").isNotEmpty())
                .andReturn();
    }

    private UserDto toUserDto(MvcResult mvcResult) throws Exception {
        var content = mvcResult.getResponse().getContentAsString();
        return mapper.readValue(content, UserDto.class);
    }
}