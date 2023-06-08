package com.example.emailsender.service;

import com.example.emailsender.model.EmailEntity;
import com.example.emailsender.model.dto.EmailDto;
import com.example.emailsender.repository.EmailRepository;
import com.example.emailsender.service.impl.EmailServiceImpl;
import com.example.emailsender.service.util.SendGridService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class EmailServiceTest {
    @Mock
    private EmailRepository emailRepositoryMock;

    @Mock
    private SendGridService sendGridServiceMock;

    @InjectMocks
    private EmailServiceImpl emailService;

    private EmailEntity emailEntity;

    @BeforeEach
    public void setUpEmailEntity() {
        emailEntity = EmailEntity.builder()
                .id(1L)
                .emailFrom("mail_sender1@example.com")
                .emailTo("email_reciver1@example.com")
                .emailSubject("subject")
                .emailText("emails body text")
                .build();
    }

    @Test
    public void sendEmail_ShouldReturnEmailDto() {
        Mockito.when(emailRepositoryMock.save(Mockito.any(EmailEntity.class))).thenReturn(emailEntity);
        Mockito.when(sendGridServiceMock.sendEmail(Mockito.any(EmailDto.class))).thenReturn(true);

        EmailDto emailDto = EmailDto.builder()
                .emailFrom("mail_sender1@example.com")
                .emailTo("email_reciver1@example.com")
                .emailSubject("subject")
                .emailText("emails body text")
                .build();

        Assertions.assertEquals(EmailDto.fromEntity(emailEntity), emailService.sendEmail(emailDto));

        Mockito.verify(sendGridServiceMock).sendEmail(emailDto);
        emailEntity.setId(null);
        Mockito.verify(emailRepositoryMock).save(emailEntity);
    }

    @Test
    public void sendEmail_ShouldReturnNull() {
        Mockito.when(sendGridServiceMock.sendEmail(Mockito.any(EmailDto.class))).thenReturn(false);

        EmailDto emailDto = EmailDto.builder()
                .emailFrom("mail_sender1@example.com")
                .emailTo("email_reciver1@example.com")
                .emailSubject("subject")
                .emailText("emails body text")
                .build();

        Assertions.assertEquals(null, emailService.sendEmail(emailDto));

        Mockito.verify(sendGridServiceMock).sendEmail(emailDto);

    }

    @Test
    public void getEmails_ShouldReturnListOfEmailDtos() {
        Mockito.when(emailRepositoryMock.findAllByEmailFrom(Mockito.anyString())).thenReturn(List.of(emailEntity));

        Assertions.assertEquals(List.of(EmailDto.fromEntity(emailEntity)), emailService.getEmails("mail_sender1@example.com"));

        Mockito.verify(emailRepositoryMock).findAllByEmailFrom("mail_sender1@example.com");
    }

    @Test
    public void getEmails_ShouldReturnEmptyList() {
        Mockito.when(emailRepositoryMock.findAllByEmailFrom(Mockito.anyString())).thenReturn(new ArrayList<>());

        Assertions.assertEquals(new ArrayList<>(), emailService.getEmails("mail_sender1@example.com"));

        Mockito.verify(emailRepositoryMock).findAllByEmailFrom("mail_sender1@example.com");
    }


}
