package edu.cnam.nfe101.kafkaconsumer.dto;

public record NewAddress(String interoperabilityKey, String streetNumber, String suffix, String cadastralParcel, String longitude, String latitude, Integer streetId) {
}
