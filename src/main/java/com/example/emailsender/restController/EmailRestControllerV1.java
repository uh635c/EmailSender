package com.example.emailsender.restController;

import com.example.emailsender.model.dto.EmailDto;
import com.example.emailsender.model.dto.EmailFromDto;
import com.example.emailsender.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Slf4j
public class EmailRestControllerV1 {

    private final EmailService emailService;

    @PostMapping("/emails")
    public ResponseEntity<EmailDto> sendEmail(@RequestBody @Valid EmailDto emailDto) {

        if (emailDto == null) {
            log.warn("Input emailDto is null");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        EmailDto emailDtoSent = emailService.sendEmail(emailDto);

        if (emailDtoSent == null) {
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }

        log.info("Email to {} is successfully sent", emailDto.getEmailTo());
        return new ResponseEntity<>(emailDtoSent, HttpStatus.OK);
    }

    @GetMapping("/emails")
    public ResponseEntity<List<EmailDto>> getEmails(@RequestBody EmailFromDto emailFromDto) {
        if (emailFromDto == null) {
            log.warn("Input emailFromDto is nul");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        List<EmailDto> listEmails = emailService.getEmails(emailFromDto.getEmailFrom());

        if (listEmails.isEmpty()) {
            log.warn("List is empty for provided {} email", emailFromDto.getEmailFrom());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        log.info("list of emails from {} is received", emailFromDto.getEmailFrom());
        return new ResponseEntity<>(listEmails, HttpStatus.OK);
    }
}
