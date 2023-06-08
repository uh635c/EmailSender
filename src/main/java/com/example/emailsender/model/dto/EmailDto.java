package com.example.emailsender.model.dto;

import com.example.emailsender.model.EmailEntity;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@Builder
public class EmailDto {
    private Long id;
    @NotNull
    private String emailFrom;
    @NotNull
    private String emailTo;
    @NotNull
    private String emailText;
    @NotNull
    private String emailSubject;

    public static EmailDto fromEntity(EmailEntity emailEntity) {
        return EmailDto.builder()
                .emailFrom(emailEntity.getEmailFrom())
                .emailTo(emailEntity.getEmailTo())
                .emailText(emailEntity.getEmailText())
                .emailSubject(emailEntity.getEmailSubject())
                .id(emailEntity.getId())
                .build();
    }

    public EmailEntity toEntity() {
        return EmailEntity.builder()
                .emailFrom(emailFrom)
                .emailTo(emailTo)
                .emailSubject(emailSubject)
                .emailText(emailText)
                .build();
    }
}
