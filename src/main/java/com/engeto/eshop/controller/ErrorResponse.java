package com.engeto.eshop.controller;

import java.time.LocalDateTime;

public class ErrorResponse {

    private String author;
    private String message;
    private StackTraceElement[] stackTrace;
    private LocalDateTime timestamp;

    public ErrorResponse() {
    }

    public ErrorResponse(String message,
                         String author,
                         LocalDateTime timestamp,
                         StackTraceElement[] stackTrace) {
        this.author = author;
        this.message = message;
        this.timestamp = timestamp;
        this.stackTrace = stackTrace;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public StackTraceElement[] getStackTrace() {
        return stackTrace;
    }

    public void setStackTrace(StackTraceElement[] stackTrace) {
        this.stackTrace = stackTrace;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
