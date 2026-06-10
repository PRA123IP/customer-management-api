package com.customer.management.api.exception;

public class InvalidCreditScoreException extends RuntimeException {

    public InvalidCreditScoreException(String message) {
        super(message);
    }
}