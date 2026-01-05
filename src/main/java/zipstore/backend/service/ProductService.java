package zipstore.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import zipstore.backend.entity.Product;
import zipstore.backend.repository.ProductRepository;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }
}
