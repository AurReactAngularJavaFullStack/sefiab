//package fr.soprasteria.agircarcco.sefiab.service;
//
//import jakarta.mail.internet.MimeMessage;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.mail.javamail.MimeMessageHelper;
//
//import javax.mail.MessagingException;
//
//import static org.mockito.Mockito.*;

//class EmailServiceTest {
//
//    private EmailService emailService;
//    private JavaMailSender javaMailSenderMock;
//    private MimeMessage mimeMessageMock;
//    private MimeMessageHelper mimeMessageHelperMock;
//
//    @BeforeEach
//    void setUp() throws MessagingException, jakarta.mail.MessagingException {
//        javaMailSenderMock = mock(JavaMailSender.class);
//        mimeMessageMock = mock(MimeMessage.class);
//        mimeMessageHelperMock = mock(MimeMessageHelper.class);
//
//        when(javaMailSenderMock.createMimeMessage()).thenReturn(mimeMessageMock);
//
//        emailService = spy(new EmailService(javaMailSenderMock));  // Use a spy here
//
//        // Override the helper creation for our tests
//        doReturn(mimeMessageHelperMock).when(emailService).createMimeMessageHelper(mimeMessageMock, true);
//    }
//
//    @Test
//    void testSendSuccessNotification() throws Exception {
//        emailService.sendSuccessNotification();
//
//        verify(mimeMessageHelperMock).setTo("aur951@hotmail.fr");
//        verify(mimeMessageHelperMock).setSubject("Success Notification");
//        verify(mimeMessageHelperMock).setText("The job was successfully completed.", true);
//        verify(javaMailSenderMock).send(mimeMessageMock);
//    }
//
//    @Test
//    void testSendFailureAlert() throws Exception {
//        emailService.sendFailureAlert();
//
//        verify(mimeMessageHelperMock).setTo("aur951@hotmail.fr");
//        verify(mimeMessageHelperMock).setSubject("Failure Alert");
//        verify(mimeMessageHelperMock).setText("The job encountered a failure.", true);
//        verify(javaMailSenderMock).send(mimeMessageMock);
//    }
//
//    @Test
//    void testSendEmail() throws Exception {
//        String to = "test@example.com";
//        String subject = "Test Subject";
//        String body = "Test Body";
//
//        emailService.sendEmail(to, subject, body);
//
//        verify(mimeMessageHelperMock).setTo(to);
//        verify(mimeMessageHelperMock).setSubject(subject);
//        verify(mimeMessageHelperMock).setText(body, true);
//        verify(javaMailSenderMock).send(mimeMessageMock);
//    }
//}


