package edu.cnam.nfe101.kafkaconsumer.rest;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import edu.cnam.nfe101.kafkaconsumer.assembler.AddressAssembler;
import edu.cnam.nfe101.kafkaconsumer.assembler.AddressDetailsAssembler;
import edu.cnam.nfe101.kafkaconsumer.dto.AddressDetails;
import edu.cnam.nfe101.kafkaconsumer.dto.AddressSummary;
import edu.cnam.nfe101.kafkaconsumer.dto.CustomItemsListModel;
import edu.cnam.nfe101.kafkaconsumer.dto.NewAddress;
import edu.cnam.nfe101.kafkaconsumer.exceptions.AddressNotFoundException;
import edu.cnam.nfe101.kafkaconsumer.exceptions.StreetNotFoundException;
import edu.cnam.nfe101.kafkaconsumer.model.Address;
import edu.cnam.nfe101.kafkaconsumer.model.Street;
import edu.cnam.nfe101.kafkaconsumer.repository.StreetRepository;
import edu.cnam.nfe101.kafkaconsumer.repository.AddressRepository;

@RestController
@RequestMapping("/addresses")
public class AddressController {
    private final AddressRepository addressRepository;

    private final StreetRepository streetRepository;

    private final AddressAssembler addressAssembler;

    private final AddressDetailsAssembler addressDetailsAssembler;

    public AddressController(AddressRepository addressRepository, StreetRepository streetRepository, AddressAssembler addressAssembler,
                          AddressDetailsAssembler addressDetailsAssembler) {
        this.addressRepository = addressRepository;
        this.streetRepository = streetRepository;
        this.addressAssembler = addressAssembler;
        this.addressDetailsAssembler = addressDetailsAssembler;
    }

    @GetMapping
    public CustomItemsListModel<AddressSummary> all() {
        List<EntityModel<AddressSummary>> addresses = addressRepository.findAll().stream()
                .map(entity -> addressAssembler.toModel(entity))
                .collect(Collectors.toList());

        return new CustomItemsListModel<>(addresses)
                .add(linkTo(methodOn(AddressController.class).all()).withSelfRel());
    }

    @PostMapping
    public ResponseEntity<?> newAddress(@RequestBody NewAddress newAddress) {
        Street street = streetRepository.findById(newAddress.streetId())
                .orElseThrow(() -> new StreetNotFoundException(newAddress.streetId()));

        Address newAddressEntity = new Address();
        newAddressEntity.setStreet(street);
        newAddressEntity.setInteroperabilityKey(newAddress.interoperabilityKey());
        newAddressEntity.setStreetNumber(newAddress.streetNumber());
        newAddressEntity.setSuffix(newAddress.suffix());
        newAddressEntity.setCadastralParcel(newAddress.cadastralParcel());
        newAddressEntity.setLongitude(newAddress.longitude());
        newAddressEntity.setLatitude(newAddress.latitude());

        EntityModel<AddressDetails> entityModel = addressDetailsAssembler.toModel(addressRepository.save(newAddressEntity));

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @GetMapping("/{id}")
    public EntityModel<AddressDetails> one(@PathVariable Integer id) {
        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new AddressNotFoundException(id));

        return addressDetailsAssembler.toModel(address);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> replaceAddress(@RequestBody Address newAddress, @PathVariable Integer id) {
        Address updatedAddress = addressRepository.findById(id)
                .map(address -> {
                    address.setInteroperabilityKey(newAddress.getInteroperabilityKey());
                    address.setStreetNumber(newAddress.getStreetNumber());
                    address.setSuffix(newAddress.getSuffix());
                    address.setCadastralParcel(newAddress.getCadastralParcel());
                    address.setLongitude(newAddress.getLongitude());
                    address.setLatitude(newAddress.getLatitude());

                    return addressRepository.save(address);
                })
                .orElseGet(() -> {
                    return addressRepository.save(newAddress);
                });

        EntityModel<AddressDetails> entityModel = addressDetailsAssembler.toModel(updatedAddress);

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAddress(@PathVariable Integer id) {
        addressRepository.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
