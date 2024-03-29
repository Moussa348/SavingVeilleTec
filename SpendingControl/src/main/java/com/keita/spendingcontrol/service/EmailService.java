package com.keita.spendingcontrol.service;

import com.keita.spendingcontrol.model.entity.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    public void confirmRegistration(Person person) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

        mimeMessageHelper.setTo(person.getEmail());
        mimeMessageHelper.setSubject(person.getLastName() + ", " + person.getFirstName() + " please confirm your registration");

        String content = "Dear [[name]],<br>"
                + "Please click the link below to verify your registration:<br>"
                + "<h3><a href=\"[[URL]]\" target=\"_self\">VERIFY</a></h3>"
                + "Thank you,<br>"
                + "Spending Control Inc.";


        content = content.replace("[[name]]", person.getFirstName());
        content = content.replace("[[URL]]", "http://localhost:5001/registrationVerifyCode/" + person.getVerificationCode());

        mimeMessageHelper.setText(content, true);

        javaMailSender.send(mimeMessageHelper.getMimeMessage());
    }

    public void resetPassword(Person person) throws MessagingException{
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

        mimeMessageHelper.setTo(person.getEmail());
        mimeMessageHelper.setSubject(person.getLastName() + ", " + person.getFirstName() + " please confirm your email to reset your password");

        String content = "Dear [[name]],<br>"
                + "Please click the link below to reset your password:<br>"
                + "<h3><a href=\"[[URL]]\" target=\"_self\">RESET</a></h3>"
                + "Thank you,<br>"
                + "Spending Control Inc.";


        content = content.replace("[[name]]", person.getFirstName());
        content = content.replace("[[URL]]", "http://localhost:5001/resetPasswordVerification/" + person.getVerificationCode());

        mimeMessageHelper.setText(content, true);

        javaMailSender.send(mimeMessageHelper.getMimeMessage());
    }

    public void fareWellMessage(Person person) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

        mimeMessageHelper.setTo(person.getEmail());
        mimeMessageHelper.setSubject(person.getLastName() + ", " + person.getFirstName() + " we are sorry to hear that you will be leaving us...");

        String content = "Dear [[name]],<br>"
                + "<br>"
                + "We hope that you enjoyed the time spent with us.<br>"
                + "We hope that we were able to help you with this application.<br>"
                + "Be sure to minimize your expenses, if you need help, we will always be there for you!<br>"
                + "<br>"
                + "Take Care,[[name]]<br>"
                + "<br>"
                + "Spending Control Inc.";


        content = content.replace("[[name]]", person.getFirstName());

        mimeMessageHelper.setText(content, true);

        javaMailSender.send(mimeMessageHelper.getMimeMessage());
    }
}
