package zipstore.backend.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import zipstore.backend.dto.OrderItemRequest;
import zipstore.backend.dto.OrderRequest;
import zipstore.backend.entity.Order;
import zipstore.backend.entity.OrderItem;
import zipstore.backend.entity.Product;
import zipstore.backend.entity.User;
import zipstore.backend.repository.OrderRepository;
import zipstore.backend.repository.ProductRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    @Transactional
    public Order placeOrder(User user, OrderRequest orderRequest) {
        Order order = new Order();
        order.setUser(user);
        order.setStatus("COMPLETED");
        order.setCreatedAt(OffsetDateTime.now());

        BigDecimal total = BigDecimal.ZERO;
        List<OrderItem> orderItemList = new ArrayList<>();

        for (OrderItemRequest orderItemRequest : orderRequest.items()) {
            Product product = productRepository.findById(orderItemRequest.productId())
                    .orElseThrow(() -> new RuntimeException("Product Not Found"));

            OrderItem orderItem = OrderItem.builder()
                    .order(order)
                    .product(product)
                    .quantity(orderItemRequest.quantity())
                    .price(product.getPrice())
                    .build();

            orderItemList.add(orderItem);

            total = total.add(product.getPrice().multiply(BigDecimal.
                    valueOf(orderItemRequest.quantity())));
        }

        order.setItems(orderItemList);
        order.setTotalPrice(total);
        return orderRepository.save(order);
    }

    public List<Order> getUsersOrders(User user) {
        return orderRepository.findByUser(user);
    }
}
