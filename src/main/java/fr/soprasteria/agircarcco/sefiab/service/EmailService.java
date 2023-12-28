package fr.soprasteria.agircarcco.sefiab.service;

import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;

@Service
@Lazy
public class EmailService {

    @Autowired
    JavaMailSender javaMailSender;

    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    // This method allows us to abstract the creation of MimeMessageHelper
    protected MimeMessageHelper createMimeMessageHelper(MimeMessage message, boolean multipart) throws MessagingException, jakarta.mail.MessagingException {
        return new MimeMessageHelper(message, multipart);
    }

    public void sendSuccessNotification() {
        // Implement email sending logic for success notification
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo("aur951@hotmail.fr");
            helper.setSubject("Success Notification");
            helper.setText("The job was successfully completed.");
            javaMailSender.send(message);
        } catch (jakarta.mail.MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendFailureAlert() {
        // Implement email sending logic for failure alert
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo("aur951@hotmail.fr");
            helper.setSubject("Failure Alert");
            helper.setText("The job encountered a failure.");
            javaMailSender.send(message);
        } catch (jakarta.mail.MessagingException e) {
            e.printStackTrace(); // Handle the exception
        }
    }


    public void sendEmail(String to, String subject, String body) throws MessagingException, jakarta.mail.MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(body, true);

        javaMailSender.send(message);
    }
}
