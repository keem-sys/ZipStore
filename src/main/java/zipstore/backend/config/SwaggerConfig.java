package zipstore.backend.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI zipStoreOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("ZipStore API")
                        .description("E-commerce backend API for managing products, orders, and authentication.")
                        .version("1.0"));
    }
}