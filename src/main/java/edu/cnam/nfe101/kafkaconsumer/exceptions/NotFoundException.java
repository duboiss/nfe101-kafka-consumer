package edu.cnam.nfe101.kafkaconsumer.exceptions;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }
}
