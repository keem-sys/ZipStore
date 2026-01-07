package zipstore.backend.service;

import com.resend.Resend;
import com.resend.services.emails.model.CreateEmailOptions;
import com.resend.services.emails.model.CreateEmailResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import zipstore.backend.dto.ContactRequest;

@Service
@RequiredArgsConstructor
public class EmailService {

    @Value("${resend.api.key}")
    private String resendApiKey;

    @Value("${admin.email}")
    private String adminEmail;

    public void sendContactEmail(ContactRequest request) {
        Resend resend = new Resend(resendApiKey);

        String htmlContent = """
            <html>
            <body style="font-family: Arial, sans-serif;">
                <div style="border: 1px solid #ddd; padding: 20px; border-radius: 10px;">
                    <h2 style="color: #007bff;">New Contact Request</h2>
                    <p><strong>Name:</strong> %s</p>
                    <p><strong>Email:</strong> %s</p>
                    <p><strong>Subject:</strong> %s</p>
                    <div style="background-color: #f9f9f9; padding: 15px; border-left: 4px solid #007bff;">
                        <p>%s</p>
                    </div>
                </div>
            </body>
            </html>
            """.formatted(
                request.getName(),
                request.getEmail(),
                request.getSubject(),
                request.getMessage().replace("\n", "<br/>")
        );

        CreateEmailOptions params = CreateEmailOptions.builder()
                .from("ZipStore System <onboarding@resend.dev>")
                .to(adminEmail)
                .subject("ZipStore Contact Form: " + request.getSubject())
                .html(htmlContent)
                .replyTo(request.getEmail())
                .build();

        try {
            CreateEmailResponse data = resend.emails().send(params);
            System.out.println("Email sent via Resend. ID: " + data.getId());
        } catch (Exception e) {
            throw new RuntimeException("Failed to send email via Resend");
        }
    }
}