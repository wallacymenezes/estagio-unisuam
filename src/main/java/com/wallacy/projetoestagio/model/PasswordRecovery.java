package com.wallacy.projetoestagio.model;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "tb_password_recovery")
public class PasswordRecovery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String otp;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private Instant expiration;

    // Constructors, getters and setters
    public PasswordRecovery() {
    }

    public PasswordRecovery(String otp, String email, Instant expiration) {
        this.otp = otp;
        this.email = email;
        this.expiration = expiration;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Instant getExpiration() {
        return expiration;
    }

    public void setExpiration(Instant expiration) {
        this.expiration = expiration;
    }
}
