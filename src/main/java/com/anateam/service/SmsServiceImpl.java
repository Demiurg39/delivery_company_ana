package com.anateam.service;

import com.anateam.dto.sms.SmsMessage;
import com.anateam.dto.sms.SmsResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
@RequiredArgsConstructor
public class SmsServiceImpl implements SmsService {

    @Value("${sms.provider.url}") private String smsProviderApi;

    @Value("${sms.provider.login}") private String login;

    @Value("${sms.provider.password}") private String password;

    @Value("${sms.provider.sender}") private String sender;

    @Value("${sms.provider.debug}") private Boolean test;

    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public void sendVerificationCode(String phoneNumber, String code) {
        String text = "Your verification code is: " + code;
        SmsMessage.SmsMessageBuilder builder = SmsMessage.builder()
            .login(login)
            .password(password)
            .id(String.valueOf(System.currentTimeMillis())) // Simple ID generation
            .sender(sender)
            .text(text)
            // TODO: fix this cause its only processed and not sending 
            .test(test ? "1" : "0")
            .phones(List.of(phoneNumber));

        SmsMessage smsMessage = builder.build();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_XML);

        HttpEntity<SmsMessage> request = new HttpEntity<>(smsMessage, headers);

        try {
            ResponseEntity<SmsResponse> response = restTemplate.postForEntity(
                smsProviderApi, request, SmsResponse.class);

            SmsResponse responseBody = response.getBody();
            if (responseBody != null) {
                log.info("SMS sent to {}. Status: {}, State: {}, Code: {}", phoneNumber,
                         responseBody.getStatus(), responseBody.getState(), code);
            } else {
                log.warn("SMS sent to {} but response body is null",
                         phoneNumber);
            }
        } catch (Exception e) {
            log.error("Failed to send SMS to {}", phoneNumber, e);
            // We might want to throw an exception here depending on
            // requirements
        }
    }
}
