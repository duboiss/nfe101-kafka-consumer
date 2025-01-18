package edu.cnam.nfe101.kafkaconsumer.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "streets")
public class Street {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "street_id")
    private Integer streetId;

    @Column(name = "street_name")
    private String name;

    @Override
    public String toString() {
        return "Street [streetId=" + streetId + ", name=" + name + "]";
    }

    public Integer getStreetId() {
        return streetId;
    }

    public void setStreetId(Integer streetId) {
        this.streetId = streetId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
