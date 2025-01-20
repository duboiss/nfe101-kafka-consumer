package edu.cnam.nfe101.kafkaconsumer.exceptions;

public class AddressNotFoundException extends NotFoundException {
    public AddressNotFoundException(Integer id) {
        super("Address not found: " + id);
    }
}
