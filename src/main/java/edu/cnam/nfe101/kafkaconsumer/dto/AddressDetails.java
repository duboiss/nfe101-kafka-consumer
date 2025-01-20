package edu.cnam.nfe101.kafkaconsumer.dto;

public record AddressDetails(Integer addressId, String streetNumber, String suffix, String cadastralParcel, String longitude, String latitude, StreetDetails street) {
}
