package com.web.recruitment.service;

import com.web.recruitment.persistence.dto.EmailDetails;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import java.util.concurrent.CompletableFuture;

public interface EmailService {
    void sendSimpleMail(EmailDetails emailDetails) throws MessagingException;
}
