package com.wallacy.projetoestagio.dto;

public record CodeConfirm() {

    private static String otp;

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        CodeConfirm.otp = otp;
    }
}
