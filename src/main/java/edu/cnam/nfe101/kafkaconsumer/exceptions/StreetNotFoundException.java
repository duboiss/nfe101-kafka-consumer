package edu.cnam.nfe101.kafkaconsumer.exceptions;

public class StreetNotFoundException extends NotFoundException{
    public StreetNotFoundException(Integer id) {
        super("Can not find street with ID: " + id);
    }
}
