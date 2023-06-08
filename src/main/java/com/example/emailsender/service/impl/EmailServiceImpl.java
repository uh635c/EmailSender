package com.example.emailsender.service.impl;

import com.example.emailsender.model.EmailEntity;
import com.example.emailsender.model.dto.EmailDto;
import com.example.emailsender.repository.EmailRepository;
import com.example.emailsender.service.EmailService;
import com.example.emailsender.service.util.SendGridService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailServiceImpl implements EmailService {

    private final EmailRepository emailRepository;
    private final SendGridService sendGridService;

    @Override
    public EmailDto sendEmail(EmailDto emailDto) {

        Boolean isSent = sendGridService.sendEmail(emailDto);

        if (!isSent) {
            return null;
        }

        EmailEntity emailEntity = emailRepository.save(emailDto.toEntity());
        log.info("EmailEntity is saved");
        return EmailDto.fromEntity(emailEntity);
    }

    @Override
    public List<EmailDto> getEmails(String emailFrom) {
        List<EmailEntity> listEmails = emailRepository.findAllByEmailFrom(emailFrom);

        if (listEmails.isEmpty()) {
            return new ArrayList<>();
        }
        log.info("List of emails is found in repository");
        return listEmails.stream().map(EmailDto::fromEntity).collect(Collectors.toList());
    }
}
