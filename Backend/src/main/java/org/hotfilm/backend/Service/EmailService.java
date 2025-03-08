package org.hotfilm.backend.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender javaMailSender;

    public void sendCodeToMail(String to, String subject, String verificationCode) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("MovieFlix <cunnconn01@gmail.com>");
        simpleMailMessage.setTo(to);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(verificationCode);

        javaMailSender.send(simpleMailMessage);
    }

    public void sendEmailWithAttachment(String to, String subject, String body, File file) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);

            // Đảm bảo chỉ có một địa chỉ trong trường "From"
            messageHelper.setFrom("MovieFlix <cunnconn01@gmail.com>"); // Địa chỉ email hợp lệ

            // Đảm bảo các địa chỉ "To" được tách ra bởi dấu phẩy
            messageHelper.setTo(to); // Ví dụ: "recipient1@example.com,recipient2@example.com"

            messageHelper.setSubject(subject);
            messageHelper.setText(body);

            // Thêm tệp đính kèm nếu có
            if (file != null && file.exists()) {
                messageHelper.addAttachment(file.getName(), file);
            }

            javaMailSender.send(mimeMessage);
        } catch (MailException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
