package zipstore.backend.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import zipstore.backend.dto.ContactRequest;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String myEmail;

    public void sendContactEmail(ContactRequest contactRequest) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage,
                    true, "UTF-8");
            mimeMessageHelper.setFrom(myEmail);
            mimeMessageHelper.setTo(myEmail);
            mimeMessageHelper.setReplyTo(contactRequest.getEmail());
            mimeMessageHelper.setSubject("ZipStore Contact Form: " + contactRequest.getSubject());

            String htmlContent = """
                <html>
                <body style="font-family: Arial, sans-serif; color: #333;">
                    <div style="max-width: 600px; margin: auto; border: 1px solid #ddd; padding: 20px; border-radius: 10px;">
                        <h2 style="color: #007bff; text-align: center;">ZipStore Contact Form</h2>
                        <hr style="border: 0; border-top: 1px solid #eee;" />
                
                        <p><strong>Name:</strong> %s</p>
                        <p><strong>Email:</strong> <a href="mailto:%s">%s</a></p>
                        <p><strong>Subject:</strong> %s</p>
                
                        <br/>
                        <div style="background-color: #f9f9f9; padding: 15px; border-radius: 5px; border-left: 4px solid #007bff;">
                            <strong>Message:</strong><br/>
                            <p style="margin-top: 5px;">%s</p>
                        </div>
                
                        <br/>
                        <hr style="border: 0; border-top: 1px solid #eee;" />
                        <p style="font-size: 12px; color: #999; text-align: center;">
                            Sent from ZipStore System
                        </p>
                    </div>
                </body>
                </html>
                """.formatted(
                    contactRequest.getName(),
                    contactRequest.getEmail(), contactRequest.getEmail(),
                    contactRequest.getSubject(),
                    contactRequest.getMessage().replace("\n", "<br/>")
            );
            mimeMessageHelper.setText(htmlContent, true);
            mailSender.send(mimeMessage);
        }  catch (MessagingException e) {
            throw new RuntimeException("Failed to send email", e);
        }
    }
}