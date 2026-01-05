package zipstore.backend.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import zipstore.backend.dto.OrderRequest;
import zipstore.backend.entity.Order;
import zipstore.backend.entity.User;
import zipstore.backend.service.OrderService;
import zipstore.backend.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final UserService userService;

    @PostMapping
    public ResponseEntity<Order> placeOrder(@RequestBody @Valid OrderRequest request,
                                            Authentication authentication) {
        User user = userService.getUserByEmail(authentication.getName());

        Order newOrder = orderService.placeOrder(user, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(newOrder);
    }

    @GetMapping
    public ResponseEntity<List<Order>> getMyOrders(Authentication authentication) {
        User user = userService.getUserByEmail(authentication.getName());

        return ResponseEntity.ok(orderService.getUsersOrders(user));
    }
}
