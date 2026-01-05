package zipstore.backend.controller;

import com.stripe.model.PaymentIntent;
import zipstore.backend.security.JwtUtils;
import zipstore.backend.service.CustomUserDetailsService;
import zipstore.backend.service.StripeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PaymentController.class)
@AutoConfigureMockMvc(addFilters = false)
public class PaymentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private StripeService stripeService;

    @MockitoBean
    private JwtUtils jwtUtils;

    @MockitoBean
    private CustomUserDetailsService customUserDetailsService;

    @Test
    public void shouldReturnClientSecret() throws Exception {
        PaymentIntent mockIntent = mock(PaymentIntent.class);

        given(mockIntent.getClientSecret()).willReturn("sk_test_123");
        given(stripeService.createPaymentIntent(anyLong())).willReturn(mockIntent);

        String requestBody = "{\"amount\": 10000}";

        mockMvc.perform(post("/api/payments/create-payment-intent")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.clientSecret").value("sk_test_123"));
    }
}