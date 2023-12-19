package com.web.recruitment.service;

import com.web.recruitment.persistence.dto.EmailDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Service
@Slf4j
@Component
public class EmailServiceImpl implements EmailService {
//    private JavaMailSender javaMailSender;

    @Override
    public void sendSimpleMail(EmailDetails emailDetails) throws MessagingException {
//        try {
//            MimeMessage message = javaMailSender.createMimeMessage();
//            message.setFrom(new InternetAddress("abc@gmail.com"));
//            message.setSubject("subject");
//            message.setText("test");
//            message.setRecipients(MimeMessage.RecipientType.TO,"englandstein@gmail.com");
//            javaMailSender.send(message);
//
//
//        } catch (MailSendException | MessagingException ex) {
//            log.warn("Sending email to " + emailDetails.getRecipient() + " failed");
//            ex.printStackTrace();
//        }
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost("smtp.gmail.com");
        javaMailSender.setPort(587);

        javaMailSender.setUsername("iwbanp.kt@gmail.com");
        javaMailSender.setPassword("pfevjmaigcjbppir");

        Properties props = javaMailSender.getJavaMailProperties();

        props.put("mail.smtp.starttls.required", "true");
        props.put("mail.smtp.ssl.protocols", "TLSv1.2");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.socketFactory.fallback", "true");

        props.put("mail.transport.protocol", "smtp");
//        props.put("mail.smtp.auth", "true");
//        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");
        props.put("mail.properties.mail.smtp.connectiontimeout", "2000");
        props.put("mail.properties.mail.smtp.timeout", "2000");
        props.put("mail.properties.mail.smtp.writetimeout", "2000");
//        spring.mail.properties.mail.smtp.connectiontimeout=5000
//        spring.mail.properties.mail.smtp.timeout=5000
//        spring.mail.properties.mail.smtp.writetimeout=5000
//


        MimeMessage message = javaMailSender.createMimeMessage();
        message.setFrom(new InternetAddress("iwbanp.kt@gmail.com"));
        message.setSubject("subject");
        message.setText(emailDetails.getBody());
        message.setRecipients(MimeMessage.RecipientType.TO, "dkhanhtoan14@gmail.com");
        javaMailSender.send(message);
    }
}
