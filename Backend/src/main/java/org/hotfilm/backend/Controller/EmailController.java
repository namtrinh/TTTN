package org.hotfilm.backend.Controller;

import org.hotfilm.backend.Service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/email")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/send-with-attachment")
    public ResponseEntity<String> sendEmailWithAttachment(
            @RequestParam String to,
            @RequestParam String subject,
            @RequestParam String body,
            @RequestParam MultipartFile file) {

        File tempFile;
        try {
            tempFile = File.createTempFile("temp", file.getOriginalFilename());
            file.transferTo(tempFile);

            emailService.sendEmailWithAttachment(to, subject, body, tempFile);

            tempFile.delete();

            return ResponseEntity.ok("Email sent successfully!");
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error while processing the file.");
        }
    }
}
