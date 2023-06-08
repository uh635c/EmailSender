package com.example.emailsender.repository;

import com.example.emailsender.model.EmailEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmailRepository extends JpaRepository<EmailEntity, Long> {

    List<EmailEntity> findAllByEmailFrom(String emailFrom);
}
