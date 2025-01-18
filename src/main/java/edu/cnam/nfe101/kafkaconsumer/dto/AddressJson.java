package edu.cnam.nfe101.kafkaconsumer.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true) // On ignore les clés envoyées par le producer qui ne sont pas ici
public class AddressJson {
    private String interoperabilityKey;
    private String address;
    private String number;
    private String suffix;
    private String cadastralParcel;
    // On ne conserve pas x et y qui sont dans le message
    private String longitude;
    private String latitude;

    public String getInteroperabilityKey() {
        return interoperabilityKey;
    }

    public void setInteroperabilityKey(String interoperabilityKey) {
        this.interoperabilityKey = interoperabilityKey;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public String getCadastralParcel() {
        return cadastralParcel;
    }

    public void setCadastralParcel(String cadastralParcel) {
        this.cadastralParcel = cadastralParcel;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }
}
