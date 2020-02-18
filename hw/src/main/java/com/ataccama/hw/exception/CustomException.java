package com.ataccama.hw.exception;

public class CustomException extends RuntimeException {
    private String message;
    private String details;

    protected CustomException() {}

    public CustomException(
            String message, String details) {
        this.message = message;
        this.details = details;
    }

    @Override
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
