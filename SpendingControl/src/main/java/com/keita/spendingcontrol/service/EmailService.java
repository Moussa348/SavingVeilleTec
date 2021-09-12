package com.keita.spendingcontrol.service;

import com.keita.spendingcontrol.model.entity.Person;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Transactional
    public void confirmRegistration(Person person) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage,true);

        mimeMessageHelper.setTo(person.getEmail());
        mimeMessageHelper.setSubject(person.getLastName() + ", " + person.getFirstName() + " please confirm your registration");

        person.setVerificationCode(RandomString.make(20));

        String content = "Dear [[name]],<br>"
                + "Please click the link below to verify your registration:<br>"
                + "<h3><a href=\"[[URL]]\" target=\"_self\">VERIFY</a></h3>"
                + "Thank you,<br>"
                + "Spending Control Inc.";


        content = content.replace("[[name]]",person.getFirstName());
        content = content.replace("[[URL]]","http://localhost:5001/verifyCode/" + person.getVerificationCode());

        mimeMessageHelper.setText(content,true);

        javaMailSender.send(mimeMessageHelper.getMimeMessage());
    }
}
