package zipstore.backend.config;

import zipstore.backend.entity.Product;
import zipstore.backend.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.util.Arrays;

@Configuration
public class DataSeeder {

    @Bean
    public CommandLineRunner initData(ProductRepository productRepository) {
        return args -> {
            if (productRepository.count() == 0) {
                Product p1 = Product.builder()
                        .name("Luxury Watch")
                        .description("Gold plated, swiss mechanics")
                        .imageUrl("https://placehold.co/600x400")
                        .price(new BigDecimal("299.99"))
                        .category("Accessories")
                        .build();

                Product p2 = Product.builder()
                        .name("Leather Bag")
                        .description("Genuine calf leather")
                        .imageUrl("https://placehold.co/600x400")
                        .price(new BigDecimal("150.50"))
                        .category("Accessories")
                        .build();

                productRepository.saveAll(Arrays.asList(p1, p2));
            }
        };
    }
}