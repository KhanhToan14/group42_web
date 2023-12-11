package com.web.recruitment.service;

import com.web.recruitment.persistence.dto.EmailDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

@Service
@Slf4j
public class EmailTrapServiceImpl implements EmailTrapService{
    @Autowired
    private JavaMailSender mailSender;
    public void sendEmail(EmailDetails emailDetails) throws Exception{
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(emailDetails.getRecipient());
        message.setSubject(emailDetails.getSubject());
        message.setText(emailDetails.getBody());
        mailSender.send(message);
    }
}
