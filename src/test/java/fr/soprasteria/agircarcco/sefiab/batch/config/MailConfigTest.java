package fr.soprasteria.agircarcco.sefiab.batch.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class MailConfigTest {

    @InjectMocks
    private MailConfig mailConfig;

    @Mock
    private MailProperties mailProperties;

    @BeforeEach
    public void setUp() {
        // Example properties you might expect from MailProperties using lenient stubbing
        lenient().when(mailProperties.getHost()).thenReturn("testHost");
        lenient().when(mailProperties.getPort()).thenReturn(1234);
        lenient().when(mailProperties.getUsername()).thenReturn("testUsername");
        lenient().when(mailProperties.getPassword()).thenReturn("testPassword");
        lenient().when(mailProperties.getProperties()).thenReturn(new HashMap<>());
    }


    @Test
    public void testMailProperties() {
        MailProperties properties = mailConfig.mailProperties();
        assertNotNull(properties);
    }

    @Test
    public void testJavaMailSender() {
        JavaMailSenderImpl sender = (JavaMailSenderImpl) mailConfig.javaMailSender();

        // Vérifier que le sender a les bonnes propriétés
        assertEquals("testHost", sender.getHost());
        assertEquals(1234, sender.getPort());
        assertEquals("testUsername", sender.getUsername());
        assertEquals("testPassword", sender.getPassword());

        // Vérifier que les propriétés JavaMail sont correctement définies
        assertNotNull(sender.getJavaMailProperties());
    }
}

