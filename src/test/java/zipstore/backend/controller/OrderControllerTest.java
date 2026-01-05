package zipstore.backend.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;
import zipstore.backend.dto.OrderItemRequest;
import zipstore.backend.dto.OrderRequest;
import zipstore.backend.entity.Order;
import zipstore.backend.entity.User;
import zipstore.backend.security.JwtUtils;
import zipstore.backend.service.CustomUserDetailsService;
import zipstore.backend.service.OrderService;
import zipstore.backend.service.UserService;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrderController.class)
@AutoConfigureMockMvc(addFilters = false)
public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private OrderService orderService;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private UserService userService;

    @MockitoBean
    private JwtUtils jwtUtils;

    @MockitoBean
    private CustomUserDetailsService customUserDetailsService;


    private User user;
    private Order order;
    private Principal mockPrincipal;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .id(1L)
                .email("test@gmail.com")
                .name("test")
                .build();

        order = Order.builder()
                .id(50L)
                .totalPrice(new BigDecimal("2500.00"))
                .status("COMPLETED")
                .build();

        mockPrincipal = new UsernamePasswordAuthenticationToken("test@gmail.com",
                "password");
    }

    @Test
    public void shouldPlaceOrderSuccessfully() throws Exception {
        OrderItemRequest itemRequest = new OrderItemRequest(1L, 2);
        OrderRequest orderRequest = new OrderRequest(List.of(itemRequest));

        given(userService.getUserByEmail("test@gmail.com")).willReturn(user);
        given(orderService.placeOrder(eq(user), any(OrderRequest.class))).willReturn(order);

        mockMvc.perform(post("/api/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .principal(mockPrincipal)
                        .content(objectMapper.writeValueAsString(orderRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(50))
                .andExpect(jsonPath("$.totalPrice").value(2500.00));
    }


    @Test
    public void shouldGetUsersOrder() throws Exception {
        given(userService.getUserByEmail("test@gmail.com")).willReturn(user);
        given(orderService.getUsersOrders(user)).willReturn(List.of(order));

        mockMvc.perform(get("/api/orders")
                        .principal(mockPrincipal))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(50));
    }

    @Test
    public void shouldReturn400WhenOrderItemsAreEmpty() throws Exception {
        OrderRequest badRequest = new OrderRequest(List.of());

        mockMvc.perform(post("/api/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .principal(mockPrincipal)
                        .content(objectMapper.writeValueAsString(badRequest)))
                .andExpect(status().isBadRequest());
    }


}
