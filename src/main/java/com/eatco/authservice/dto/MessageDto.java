package com.eatco.authservice.dto;

import lombok.Data;

@Data
public class MessageDto {

    private String status;
    private String message;

    public MessageDto() {
    }

    public MessageDto(String status) {
        this.status = status;
    }

    public MessageDto(String status, String message) {
        this.status = status;
        this.message = message;
    }
}
