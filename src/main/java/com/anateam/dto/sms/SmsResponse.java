package com.anateam.dto.sms;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import lombok.Data;

@Data
@JacksonXmlRootElement(localName = "response")
public class SmsResponse {

    @JacksonXmlProperty(localName = "status")
    private Integer status;

    @JacksonXmlProperty(localName = "state")
    private Integer state;

    @JacksonXmlProperty(localName = "account")
    private String account;

    @JacksonXmlProperty(localName = "smsprice")
    private Double smsPrice;
}
