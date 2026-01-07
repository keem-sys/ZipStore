package zipstore.backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zipstore.backend.dto.ContactRequest;
import zipstore.backend.service.EmailService;

@RestController
@RequestMapping("/api/contact")
@RequiredArgsConstructor
public class ContactController {

    private final EmailService emailService;

    @PostMapping
    public ResponseEntity<String> sendContactMessage(@RequestBody ContactRequest request) {
        emailService.sendContactEmail(request);
        return ResponseEntity.ok("Message sent successfully");
    }
}