package zipstore.backend.service;

import org.junit.jupiter.api.BeforeEach;
import zipstore.backend.dto.OrderItemRequest;
import zipstore.backend.dto.OrderRequest;
import zipstore.backend.entity.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import zipstore.backend.entity.Product;
import zipstore.backend.entity.User;
import zipstore.backend.repository.OrderRepository;
import zipstore.backend.repository.ProductRepository;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @Mock
    OrderRepository orderRepository;
    @Mock
    ProductRepository productRepository;

    @InjectMocks
    OrderService orderService;

    private User user;
    private Product laptop;

    @BeforeEach
    public void setup() {
        user = User.builder()
                .id(1L)
                .name("test")
                .email("test@gmail.com")
                .build();

        laptop = Product.builder()
                .id(103L)
                .name("MacBook Air M2")
                .price(new BigDecimal("20000.00"))
                .category("Computers")
                .stockQuantity(10)
                .build();

    }

    @Test
    public void shouldPlaceOrderSuccessfully() {
        OrderItemRequest itemRequest = new OrderItemRequest(103L, 2);
        OrderRequest orderRequest = new OrderRequest(List.of(itemRequest));

        when(productRepository.findById(103L)).thenReturn(java.util.Optional.of(laptop));
        when(orderRepository.save(org.mockito.ArgumentMatchers.any(Order.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Order savedOrder = orderService.placeOrder(user, orderRequest);
        assertEquals(new BigDecimal("40000.00"), savedOrder.getTotalPrice());
        assertEquals("COMPLETED", savedOrder.getStatus());
        assertEquals(user, savedOrder.getUser());
    }

    @Test
    public void shouldGetUsersOrder() {
        Order fakeOrder = new Order();
        when(orderRepository.findByUser(user)).thenReturn(List.of(fakeOrder));

        List<Order> orders = orderService.getUsersOrders(user);
        assertEquals(1, orders.size(), "Should return 1 order");
    }

    @Test
    public void shouldThrowExceptionWhenStockIsLow() {
        OrderItemRequest orderItemRequest = new OrderItemRequest(103L, 11);
        OrderRequest orderRequest = new OrderRequest(List.of(orderItemRequest));

        when(productRepository.findById(103L)).thenReturn(java.util.Optional.of(laptop));

        org.junit.jupiter.api.Assertions.assertThrows(RuntimeException.class, () -> {
            orderService.placeOrder(user, orderRequest);
        });
    }
}
