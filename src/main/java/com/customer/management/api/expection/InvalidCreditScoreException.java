package com.customer.management.api.expection;

public class InvalidCreditScoreException extends RuntimeException {

    public InvalidCreditScoreException(String message) {
        super(message);
    }
}