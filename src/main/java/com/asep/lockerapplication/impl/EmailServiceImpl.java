package com.asep.lockerapplication.impl;

import com.asep.lockerapplication.entity.LockerPassword;
import com.asep.lockerapplication.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    @Async
    @Override
    public void sendLockerCredential(String to, List<LockerPassword> lps) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to);
            message.setSubject("Locker Booking Information");
            message.setText(buildMessage(lps));

            mailSender.send(message);

            log.info("Email sent to {}", to);

        } catch (Exception e) {
            log.error("Failed to send email", e);
        }
    }


    private String buildMessage(List<LockerPassword> lps) {
        StringBuilder detailBuilder = new StringBuilder();
        for (LockerPassword lp : lps) {
            detailBuilder.append(String.format("""
                Nomor Locker   : %s
                Password          : %s
                """, lp.getLocker().getLockerNumber(), lp.getPassword()));
            detailBuilder.append("\n");
        }

        return """
            Booking Locker Berhasil!
            
            %s
            
            Catatan:
            - Password hanya bisa digunakan 2 kali
            - Jangan bagikan password ke siapa pun

            Terima kasih 🙏
            """.formatted(detailBuilder.toString().trim());
    }
}

