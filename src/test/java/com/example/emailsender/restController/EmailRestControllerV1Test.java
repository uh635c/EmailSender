package com.example.emailsender.restController;

import com.example.emailsender.model.dto.EmailDto;
import com.example.emailsender.service.EmailService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class EmailRestControllerV1Test {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmailService emailServiceMock;

    private EmailDto emailDto;

    @BeforeEach
    public void setUpEmailDto() {
        emailDto = EmailDto.builder()
                .id(1L)
                .emailFrom("mail_sender1@example.com")
                .emailTo("email_reciver1@example.com")
                .emailSubject("subject")
                .emailText("emails body text")
                .build();
    }

    @Test
    public void sendEmail_ShouldReturnEmailDto() throws Exception {
        Mockito.when(emailServiceMock.sendEmail(Mockito.any(EmailDto.class))).thenReturn(emailDto);

        String jsonExpected = new ObjectMapper().writeValueAsString(emailDto);

        mockMvc.perform(post("/api/v1/emails")
                        .contentType("application/json")
                        .content("{\"emailFrom\":\"mail_sender1@example.com\",\n" +
                                "\"emailTo\":\"email_reciver1@example.com\",\n" +
                                "\"emailText\":\"emails body text\",\n" +
                                "\"emailSubject\":\"subject\"}"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(jsonExpected));

        emailDto.setId(null);
        Mockito.verify(emailServiceMock).sendEmail(emailDto);
    }

    @Test
    public void sendEmail_ShouldReturnBadRequest() throws Exception {

        mockMvc.perform(post("/api/v1/emails")
                        .contentType("application/json")
                        .content(""))
                .andDo(print())
                .andExpect(status().isBadRequest());

    }

    @Test
    public void sendEmail_ShouldReturnExpectationFailed() throws Exception {
        Mockito.when(emailServiceMock.sendEmail(Mockito.any(EmailDto.class))).thenReturn(null);

        mockMvc.perform(post("/api/v1/emails")
                        .contentType("application/json")
                        .content("{\"emailFrom\":\"mail_sender1@example.com\",\n" +
                                "\"emailTo\":\"email_reciver1@example.com\",\n" +
                                "\"emailText\":\"emails body text\",\n" +
                                "\"emailSubject\":\"subject\"}"))
                .andDo(print())
                .andExpect(status().isExpectationFailed());

        emailDto.setId(null);
        Mockito.verify(emailServiceMock).sendEmail(emailDto);
    }

    @Test
    public void getEmails_ShouldReturnListOfEmailsByEmailFrom() throws Exception {
        Mockito.when(emailServiceMock.getEmails("email_sender1@example.com")).thenReturn(List.of(emailDto));

        String jsonExpected = new ObjectMapper().writeValueAsString(List.of(emailDto));

        mockMvc.perform(get("/api/v1/emails")
                        .contentType("application/json")
                        .content("{\"emailFrom\":\"email_sender1@example.com\"}"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(jsonExpected));

        Mockito.verify(emailServiceMock).getEmails("email_sender1@example.com");
    }

    @Test
    public void getEmails_ShouldReturnBadRequest() throws Exception {

        mockMvc.perform(get("/api/v1/emails")
                        .contentType("application/json")
                        .content(""))
                .andDo(print())
                .andExpect(status().isBadRequest());

    }

    @Test
    public void getEmails_ShouldReturnEmptyListOfEmailsByEmailFrom() throws Exception {
        Mockito.when(emailServiceMock.getEmails("email_sender1@example.com")).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/api/v1/emails")
                        .contentType("application/json")
                        .content("{\"emailFrom\":\"email_sender1@example.com\"}"))
                .andDo(print())
                .andExpect(status().isNotFound());

        Mockito.verify(emailServiceMock).getEmails("email_sender1@example.com");
    }
}
