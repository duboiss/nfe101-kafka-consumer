package edu.cnam.nfe101.kafkaconsumer.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import edu.cnam.nfe101.kafkaconsumer.dto.AddressJson;
import edu.cnam.nfe101.kafkaconsumer.model.Address;
import edu.cnam.nfe101.kafkaconsumer.model.Street;
import edu.cnam.nfe101.kafkaconsumer.repository.AddressRepository;
import edu.cnam.nfe101.kafkaconsumer.repository.StreetRepository;

@Service
public class AddressService {
    private static final Logger log = LoggerFactory.getLogger(AddressService.class);

    private final AddressRepository addressRepository;
    private final StreetRepository streetRepository;

    public AddressService(AddressRepository addressRepository, StreetRepository streetRepository) {
        this.addressRepository = addressRepository;
        this.streetRepository = streetRepository;
    }

    public void save(AddressJson json) {
        Street street;
        try {
            street = getOrSaveStreet(json);
        } catch (Exception e) {
            log.error("Error getting/saving street from address object. Street name: {}", json.getAddress(), e);
            throw new RuntimeException(e);
        }

        try {
            saveAddress(json, street);
        } catch (Exception e) {
            log.error("Error saving address object in DB {}", json, e);
            throw new RuntimeException(e);
        }


    }

    private void saveAddress(AddressJson json, Street street) {
        Address address = addressRepository.findByCadastralParcel(json.getCadastralParcel())
                .orElseGet(() -> new Address());
        address.setStreet(street);
        address.setInteroperabilityKey(json.getInteroperabilityKey());
        address.setStreetNumber(json.getNumber());
        address.setSuffix(json.getSuffix());
        address.setCadastralParcel(json.getCadastralParcel());
        address.setLongitude(json.getLongitude());
        address.setLatitude(json.getLatitude());
        addressRepository.save(address);
    }

    private Street getOrSaveStreet(AddressJson json) {
        String streetName = Optional.ofNullable(json.getAddress())
                .map(s -> s.trim())
                .orElse("N/A");
        Optional<Street> byStreetName = streetRepository.findByName(streetName);
        Street street = byStreetName.orElseGet(() -> {
            Street newStreet = new Street();
            newStreet.setName(json.getAddress());
            return streetRepository.save(newStreet);
        });
        return street;
    }
}
