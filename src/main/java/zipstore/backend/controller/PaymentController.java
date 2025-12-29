package zipstore.backend.controller;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import jakarta.annotation.PostConstruct; // Important for initialization
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    @Value("${STRIPE_SECRET}")
    private String stripeSecretKey;

    private static final Logger logger = LoggerFactory.getLogger(PaymentController.class);

    @PostConstruct
    public void init() {
        Stripe.apiKey = stripeSecretKey;
    }

    @PostMapping("/create-payment-intent")
    public ResponseEntity<Map<String, String>> createPaymentIntent(@RequestBody Map<String, Object> data) {
        logger.info("Received request to create Payment Intent");
        try {
            long amount = ((Number) data.get("amount")).longValue();

            logger.info("Amount: {} cents (R {})", amount, amount / 100.0);

            PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                    .setAmount(amount)
                    .setCurrency("zar")
                    .setAutomaticPaymentMethods(
                            PaymentIntentCreateParams.AutomaticPaymentMethods.builder()
                                    .setEnabled(true)
                                    .build()
                    )
                    .build();

            PaymentIntent intent = PaymentIntent.create(params);

            logger.info("Payment Intent created successfully. ID: {}", intent.getId());

            Map<String, String> response = new HashMap<>();
            response.put("clientSecret", intent.getClientSecret());

            return ResponseEntity.ok(response);

        } catch (StripeException e) {
            logger.error("Stripe Error: ", e);
            return ResponseEntity.status(500).body(Map.of("error", e.getMessage()));
        }
    }
}