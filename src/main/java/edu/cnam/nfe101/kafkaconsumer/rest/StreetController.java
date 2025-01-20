package edu.cnam.nfe101.kafkaconsumer.rest;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import edu.cnam.nfe101.kafkaconsumer.assembler.AddressAssembler;
import edu.cnam.nfe101.kafkaconsumer.assembler.StreetAssembler;
import edu.cnam.nfe101.kafkaconsumer.dto.StreetDetails;
import edu.cnam.nfe101.kafkaconsumer.dto.AddressSummary;
import edu.cnam.nfe101.kafkaconsumer.dto.CustomItemsListModel;
import edu.cnam.nfe101.kafkaconsumer.dto.NewStreet;
import edu.cnam.nfe101.kafkaconsumer.exceptions.StreetNotFoundException;
import edu.cnam.nfe101.kafkaconsumer.model.Street;
import edu.cnam.nfe101.kafkaconsumer.repository.StreetRepository;
import edu.cnam.nfe101.kafkaconsumer.repository.AddressRepository;

@RestController
@RequestMapping("/streets")
public class StreetController {

    private final StreetRepository streetRepository;
    private final StreetAssembler streetAssembler;
    private final AddressRepository addressRepository;
    private final AddressAssembler addressAssembler;

    public StreetController(StreetRepository streetRepository, StreetAssembler streetAssembler,
                            AddressRepository addressRepository, AddressAssembler addressAssembler) {
        this.streetRepository = streetRepository;
        this.streetAssembler = streetAssembler;
        this.addressRepository = addressRepository;
        this.addressAssembler = addressAssembler;
    }

    @GetMapping
    public CustomItemsListModel<StreetDetails> all() {

        List<EntityModel<StreetDetails>> streets = streetRepository.findAll().stream()
                .map(streetAssembler::toModel)
                .collect(Collectors.toList());

        CustomItemsListModel<StreetDetails> streetsModel = new CustomItemsListModel<>(streets)
                .add(linkTo(methodOn(AddressController.class).all()).withSelfRel());

        return streetsModel;
    }

    @PostMapping
    public ResponseEntity<?> newStreet(@RequestBody NewStreet newStreet) {
        Street newStreetEntity = new Street();
        newStreetEntity.setName(newStreet.name());

        EntityModel<StreetDetails> entityModel = streetAssembler.toModel(streetRepository.save(newStreetEntity));

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @GetMapping("/{id}")
    public EntityModel<StreetDetails> one(@PathVariable Integer id) {
        Street street = streetRepository.findById(id)
                .orElseThrow(() -> new StreetNotFoundException(id));

        return streetAssembler.toModel(street);
    }

    @GetMapping("/{id}/addresses")
    public CustomItemsListModel<AddressSummary> addresses(@PathVariable Integer id) {
        List<EntityModel<AddressSummary>> addresses = addressRepository.findByStreetStreetId(id).stream()
                .map(addressAssembler::toModel)
                .collect(Collectors.toList());
        CustomItemsListModel<AddressSummary> addressesModel = new CustomItemsListModel<>(addresses)
                .add(linkTo(methodOn(StreetController.class).one(id)).withSelfRel());

        return addressesModel;
    }
}
