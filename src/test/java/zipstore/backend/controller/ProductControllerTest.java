package zipstore.backend.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import zipstore.backend.entity.Product;
import zipstore.backend.repository.ProductRepository;
import zipstore.backend.security.JwtUtils;
import zipstore.backend.service.CustomUserDetailsService;
import zipstore.backend.service.ProductService;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.List;


@WebMvcTest(ProductController.class)
@AutoConfigureMockMvc(addFilters = false)
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProductService productService;

    @MockitoBean
    private JwtUtils jwtUtils;

    @MockitoBean
    private CustomUserDetailsService customUserDetailsService;

    private Product laptop;
    private Product headphone;

    @BeforeEach
    public void setup() {
        laptop = Product.builder()
                .id(1L)
                .name("MacBook Air M2")
                .price(new BigDecimal("20000.00"))
                .category("Computers")
                .stockQuantity(10)
                .build();

        headphone = Product.builder()
                .id(2L)
                .name("Air pod Max")
                .price(new BigDecimal("18000.00"))
                .category("Accessories")
                .stockQuantity(5)
                .build();
    }


    @Test
    public void shouldReturnAllProducts() throws Exception {
        given(productService.getAllProducts()).willReturn(List.of(laptop, headphone));

        mockMvc.perform(get("/api/products")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("MacBook Air M2"))
                .andExpect(jsonPath("$[0].price").value(20000.00))
                .andExpect(jsonPath("$[1].name").value("Air pod Max"))
                .andExpect(jsonPath("$[1].stockQuantity").value(5)
        );

    }

    @Test
    public void shouldReturnProductById() throws Exception {
        Long productId = 1L;
        given(productService.getProductById(productId)).willReturn(java.util.Optional.of(laptop));

        mockMvc.perform(get("/api/products/{id}", productId)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("MacBook Air M2"));
    }

    @Test
    public void shouldReturn404WhenProductNotFound() throws Exception {
        Long missingId = 99L;
        given(productService.getProductById(missingId)).willReturn(java.util.Optional.empty());

        mockMvc.perform(get("/api/products/{id}", missingId))
                .andExpect(status().isNotFound());
    }

}
