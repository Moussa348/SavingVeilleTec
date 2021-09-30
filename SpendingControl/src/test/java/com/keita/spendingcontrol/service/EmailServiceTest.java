package com.keita.spendingcontrol.service;

import com.keita.spendingcontrol.model.entity.Person;
import net.bytebuddy.utility.RandomString;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.javamail.JavaMailSender;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmailServiceTest {

    @Mock
    JavaMailSender javaMailSender;

    @InjectMocks
    EmailService emailService;

    @Test
    void confirmRegistration() throws MessagingException {
        //ARRANGE
        MimeMessage mimeMessage = new MimeMessage((Session)null);
        Person person = Person.builder().email("tasadsa@gmail.com").verificationCode(RandomString.make(20)).firstName("massou").lastName("massou").build();

        when(javaMailSender.createMimeMessage()).thenReturn(mimeMessage);

        //ACT
        emailService.confirmRegistration(person);

        //ASSERT
        verify(javaMailSender,times(1)).send(mimeMessage);
    }

    @Test
    void fareWellMessage() throws MessagingException {
        //ARRANGE
        MimeMessage mimeMessage = new MimeMessage((Session)null);
        Person person = Person.builder().email("tasadsa@gmail.com").firstName("massou").lastName("massou").build();

        when(javaMailSender.createMimeMessage()).thenReturn(mimeMessage);

        //ACT
        emailService.fareWellMessage(person);

        //ASSERT
        verify(javaMailSender,times(1)).send(mimeMessage);
    }
}
