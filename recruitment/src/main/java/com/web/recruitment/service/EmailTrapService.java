package com.web.recruitment.service;

import com.web.recruitment.persistence.dto.EmailDetails;

public interface EmailTrapService {
    void sendEmail(EmailDetails emailDetails) throws Exception;
}
