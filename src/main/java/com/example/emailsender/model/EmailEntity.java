package com.example.emailsender.model;

import lombok.*;

import javax.persistence.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "emails")
public class EmailEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "EMAILFROM")
    private String emailFrom;

    @Column(name = "EMAILTO")
    private String emailTo;

    @Column(name = "TEXT")
    private String emailText;

    @Column(name = "SUBJECT")
    private String emailSubject;
}
