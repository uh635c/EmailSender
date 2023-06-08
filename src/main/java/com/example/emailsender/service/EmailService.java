package com.example.emailsender.service;

import com.example.emailsender.model.dto.EmailDto;

import java.util.List;

public interface EmailService {
    EmailDto sendEmail(EmailDto emailDto);

    List<EmailDto> getEmails(String emailFrom);
}
