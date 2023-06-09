package com.example.emailsender.configuration;

import com.sendgrid.SendGrid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {
    @Value("${api.key}")
    private String apiKey;

    @Bean
    public SendGrid sg() {
        return new SendGrid(apiKey);
    }
}
