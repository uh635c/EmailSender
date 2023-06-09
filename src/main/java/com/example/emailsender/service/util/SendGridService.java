package com.example.emailsender.service.util;

import com.example.emailsender.model.dto.EmailDto;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Slf4j
public class SendGridService {

    @Autowired
    private SendGrid sg;

    public Boolean sendEmail(EmailDto emailDto) {

        Email from = new Email(emailDto.getEmailFrom());
        String subject = emailDto.getEmailSubject();
        Email to = new Email(emailDto.getEmailTo());
        Content content = new Content("text/plain", emailDto.getEmailText());
        Mail mail = new Mail(from, subject, to, content);

        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);

            if (response.getStatusCode() == 200 || response.getStatusCode() == 201) {
                return true;
            }
            log.warn("An email has not been sent: {}", response.getBody());

        } catch (IOException ex) {
            log.error("Exception during sending an email: {}", ex, ex.getMessage());
        }

        return false;
    }
}
