package zipstore.backend.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import zipstore.backend.entity.Product;
import zipstore.backend.repository.ProductRepository;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    ProductRepository productRepository;

    @InjectMocks
    ProductService productService;

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
    public void shouldReturnAllProducts() {
        List<Product> mockList = Arrays.asList(laptop, headphone);
        when(productRepository.findAll()).thenReturn(mockList);

        List<Product> products = productService.getAllProducts();

        assertEquals(2, products.size(), "The list should contain 2 products");
        assertEquals("MacBook Air M2", products.getFirst().getName());
        assertEquals("Air pod Max", products.get(1).getName());
    }
}
