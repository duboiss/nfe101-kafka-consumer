package edu.cnam.nfe101.kafkaconsumer.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import edu.cnam.nfe101.kafkaconsumer.dto.StreetDetails;
import edu.cnam.nfe101.kafkaconsumer.dto.AddressDetails;
import edu.cnam.nfe101.kafkaconsumer.model.Address;
import edu.cnam.nfe101.kafkaconsumer.rest.AddressController;

@Component
public class AddressDetailsAssembler implements RepresentationModelAssembler<Address, EntityModel<AddressDetails>> {

    private final StreetAssembler streetAssembler;

    public AddressDetailsAssembler(StreetAssembler streetAssembler) {
        this.streetAssembler = streetAssembler;
    }

    @Override
    public EntityModel<AddressDetails> toModel(Address entity) {
        EntityModel<StreetDetails> street = streetAssembler.toModel(entity.getStreet());
        Link streetLink = street.getRequiredLink(IanaLinkRelations.SELF).withRel("street");
        AddressDetails addressDetails = new AddressDetails(entity.getAddressId(), entity.getStreetNumber(), entity.getSuffix(), entity.getCadastralParcel(), entity.getLongitude(), entity.getLatitude(), street.getContent());

        return EntityModel.of(addressDetails,
                linkTo(methodOn(AddressController.class).one(entity.getAddressId())).withSelfRel(),
                linkTo(methodOn(AddressController.class).all()).withRel("addresses"),
                streetLink);
    }
}
