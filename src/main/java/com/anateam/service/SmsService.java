package com.anateam.service;

public interface SmsService {
    void sendVerificationCode(String phoneNumber, String code);
}
