package zipstore.backend.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;
import zipstore.backend.dto.LoginRequest;
import zipstore.backend.dto.RegisterRequest;
import zipstore.backend.security.JwtUtils;
import zipstore.backend.service.AuthService;
import zipstore.backend.service.CustomUserDetailsService;
import java.security.Principal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthController.class)
@AutoConfigureMockMvc(addFilters = false)
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private JwtUtils jwtUtils;

    @MockitoBean
    private CustomUserDetailsService customUserDetailsService;

    @MockitoBean
    AuthService authService;

    @Test
    public void shouldLoginSuccessfully() throws Exception {
        LoginRequest loginRequest = new LoginRequest("test@gmail.com", "password");
        String fakeToken = "fake-token";
        given(authService.login(any(LoginRequest.class))).willReturn(fakeToken);

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(content().string(fakeToken));
    }

    @Test
    public void shouldReturn400WhenRegisterDataIsInvalid() throws Exception {
        RegisterRequest badRequest = new RegisterRequest("test",
                "not-an-email", "123");

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(badRequest)))
                .andExpect(status().isBadRequest());
    }
    @Test
    public void shouldRegisterUserSuccessfully() throws Exception {
        RegisterRequest registerRequest = new RegisterRequest("Test User",
                "test@gmail.com", "password123");

        given(authService.register(any(RegisterRequest.class))).willReturn("User registered " +
                "successfully");

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerRequest)))
                .andExpect(status().isOk())
                .andExpect(content().string("User registered successfully"));
    }

    @Test
    public void shouldGetMe() throws Exception {
        Principal mockPrincipal = new UsernamePasswordAuthenticationToken("test@gmail.com",
                null);

        mockMvc.perform(get("/api/auth/me")
                        .principal(mockPrincipal))
                .andExpect(status().isOk())
                .andExpect(content().string("Hello test@gmail.com"));
    }
}