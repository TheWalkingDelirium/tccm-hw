package com.ataccama.hw.exception;

public class CustomExceptionSchema {
    private String message;
    private String details;

    public CustomExceptionSchema() {}

    public CustomExceptionSchema(String message, String details) {
        this.message = message;
        this.details = details;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
