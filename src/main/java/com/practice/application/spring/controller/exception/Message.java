package com.practice.application.spring.controller.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class Message {

    private final HttpStatus status;
    private final String date;
    private final String content;

    public Message(HttpStatus status, String date, String content) {
        this.status = status;
        this.date = date;
        this.content = content;
    }
}
