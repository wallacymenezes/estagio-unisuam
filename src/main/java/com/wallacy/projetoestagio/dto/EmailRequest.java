package com.wallacy.projetoestagio.dto;

public record EmailRequest() {

    public static String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        EmailRequest.email = email;
    }
}
