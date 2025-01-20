package edu.cnam.nfe101.kafkaconsumer.dto;

public record AddressSummary(Integer addressId, String streetNumber, String suffix, String cadastralParcel, String longitude, String latitude) {
}
