package com.anateam.dto.sms;

import java.util.List;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JacksonXmlRootElement(localName = "message")
public class SmsMessage {

    @JacksonXmlProperty(localName = "login")
    private String login;

    @JacksonXmlProperty(localName = "pwd")
    private String password;

    @JacksonXmlProperty(localName = "id")
    private String id;

    @JacksonXmlProperty(localName = "sender")
    private String sender;

    @JacksonXmlProperty(localName = "text")
    private String text;

    @JacksonXmlElementWrapper(localName = "phones")
    @JacksonXmlProperty(localName = "phone")
    private List<String> phones;

    @JacksonXmlProperty(localName = "test")
    private String test;
}
