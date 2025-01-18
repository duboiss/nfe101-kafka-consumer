package edu.cnam.nfe101.kafkaconsumer.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "addresses")
public class Address {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "address_id")
    private Integer addressId;

    @Column(name = "address_interoperabilityKey")
    private String interoperabilityKey;

    @Column(name = "address_street_number")
    private String streetNumber;

    @Column(name = "address_suffix")
    private String suffix;

    @Column(name = "address_cadastral_parcel")
    private String cadastralParcel;

    @Column(name = "address_longitude")
    private String longitude;

    @Column(name = "address_latitude")
    private String latitude;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "street_id")
    private Street street;

    @Override
    public String toString() {
        return "Address [addressId=" + addressId + ", interoperabilityKey=" + interoperabilityKey + "]";
    }

    public Integer getAddressId() {
        return addressId;
    }

    public void setAddressId(Integer addressId) {
        this.addressId = addressId;
    }

    public String getInteroperabilityKey() {
        return interoperabilityKey;
    }

    public void setInteroperabilityKey(String interoperabilityKey) {
        this.interoperabilityKey = interoperabilityKey;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
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

    public Street getStreet() {
        return street;
    }

    public void setStreet(Street street) {
        this.street = street;
    }
}
