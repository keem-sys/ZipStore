package zipstore.backend.config;

import zipstore.backend.entity.Product;
import zipstore.backend.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
public class DataSeeder {

    @Bean
    public CommandLineRunner initData(ProductRepository productRepository) {
        return args -> {
            if (productRepository.count() == 0) {
                List<Product> products = new ArrayList<>();

                products.add(Product.builder()
                        .name("Sony WH-1000XM5")
                        .description("Industry-leading noise canceling headphones with 30-hour battery life and crystal clear hands-free calling.")
                        .imageUrl("https://images.unsplash.com/photo-1618366712010-f4ae9c647dcb?q=80&w=800&auto=format&fit=crop")
                        .price(new BigDecimal("6999.00"))
                        .category("Audio")
                        .stockQuantity(15)
                        .build());

                products.add(Product.builder()
                        .name("MacBook Air M2")
                        .description("Supercharged by M2. 13.6-inch Liquid Retina display, 8GB RAM, 256GB SSD storage.")
                        .imageUrl("https://images.unsplash.com/photo-1611186871348-b1ce696e52c9?q=80&w=800&auto=format&fit=crop")
                        .price(new BigDecimal("18999.00"))
                        .category("Computers")
                        .stockQuantity(8)
                        .build());

                products.add(Product.builder()
                        .name("Keychron K2 Pro")
                        .description("Wireless mechanical keyboard with RGB backlight and hot-swappable Gateron switches.")
                        .imageUrl("https://images.unsplash.com/photo-1595225476474-87563907a212?q=80&w=800&auto=format&fit=crop")
                        .price(new BigDecimal("1850.00"))
                        .category("Accessories")
                        .stockQuantity(40)
                        .build());

                products.add(Product.builder()
                        .name("Apple Watch Series 9")
                        .description("Advanced health sensors, always-on Retina display, and crash detection features.")
                        .imageUrl("https://images.unsplash.com/photo-1546868871-7041f2a55e12?q=80&w=800&auto=format&fit=crop")
                        .price(new BigDecimal("8499.00"))
                        .category("Wearables")
                        .stockQuantity(20)
                        .build());

                products.add(Product.builder()
                        .name("Logitech G Pro X")
                        .description("Ultra-lightweight wireless gaming mouse designed for esports professionals.")
                        .imageUrl("https://images.unsplash.com/photo-1615663245857-ac93bb7c39e7?q=80&w=800&auto=format&fit=crop")
                        .price(new BigDecimal("2499.00"))
                        .category("Gaming")
                        .stockQuantity(12)
                        .build());

                products.add(Product.builder()
                        .name("Sony Alpha a7 IV")
                        .description("Full-frame mirrorless camera with 33MP sensor and 4K 60p video recording.")
                        .imageUrl("https://images.unsplash.com/photo-1516035069371-29a1b244cc32?q=80&w=800&auto=format&fit=crop")
                        .price(new BigDecimal("45000.00"))
                        .category("Cameras")
                        .stockQuantity(3)
                        .build());

                products.add(Product.builder()
                        .name("PlayStation 5")
                        .description("Experience lightning fast loading with an ultra-high speed SSD and haptic feedback.")
                        .imageUrl("https://images.unsplash.com/photo-1606144042614-b2417e99c4e3?q=80&w=800&auto=format&fit=crop")
                        .price(new BigDecimal("11999.00"))
                        .category("Gaming")
                        .stockQuantity(10)
                        .build());

                products.add(Product.builder()
                        .name("Dell UltraSharp 27")
                        .description("27-inch 4K USB-C Hub Monitor with realistic color and InfinityEdge display.")
                        .imageUrl("https://images.unsplash.com/photo-1527443224154-c4a3942d3acf?q=80&w=800&auto=format&fit=crop")
                        .price(new BigDecimal("7500.00"))
                        .category("Computers")
                        .stockQuantity(2)
                        .build());

                productRepository.saveAll(products);
                System.out.println("Database seeded");
            }
        };
    }
}