package edu.cnam.nfe101.kafkaconsumer.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import edu.cnam.nfe101.kafkaconsumer.dto.AddressSummary;
import edu.cnam.nfe101.kafkaconsumer.model.Address;
import edu.cnam.nfe101.kafkaconsumer.rest.AddressController;

@Component
public class AddressAssembler implements RepresentationModelAssembler<Address, EntityModel<AddressSummary>> { // Transformation de "Address" model vers "AddressSummary" DTO

    @Override
    public EntityModel<AddressSummary> toModel(Address entity) {
        // Les propriétés que l'on utilise dans le DTO
        AddressSummary summary = new AddressSummary(
                entity.getAddressId(),
                entity.getStreetNumber(),
                entity.getSuffix(),
                entity.getCadastralParcel(),
                entity.getLongitude(),
                entity.getLatitude());

        return EntityModel.of(summary,
                linkTo(methodOn(AddressController.class).one(entity.getAddressId())).withSelfRel(), // Accès aux détails avec le lien
                linkTo(methodOn(AddressController.class).all()).withRel("addresses"));
    }
}
