package com.engeto.eshop.exception;

import java.time.LocalDateTime;

public class ErrorResponse {

    private LocalDateTime timestamp;
    private String author;
    private String message;
    private String description;
    private StackTraceElement[] stackTrace;

    public ErrorResponse() {
    }

    public ErrorResponse(LocalDateTime timestamp,
                         String message,
                         String author,
                         String description,
                         StackTraceElement[] stackTrace) {
        this.timestamp = timestamp;
        this.author = author;
        this.message = message;
        this.description = description;
        this.stackTrace = stackTrace;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
