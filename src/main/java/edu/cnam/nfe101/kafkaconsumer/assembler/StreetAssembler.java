package edu.cnam.nfe101.kafkaconsumer.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import edu.cnam.nfe101.kafkaconsumer.dto.StreetDetails;
import edu.cnam.nfe101.kafkaconsumer.rest.StreetController;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import edu.cnam.nfe101.kafkaconsumer.model.Street;

@Component
public class StreetAssembler implements RepresentationModelAssembler<Street, EntityModel<StreetDetails>> {

    @Override
    public EntityModel<StreetDetails> toModel(Street entity) {
        StreetDetails details = new StreetDetails(entity.getStreetId(), entity.getName());
        return EntityModel.of(details,
                linkTo(methodOn(StreetController.class).one(entity.getStreetId())).withSelfRel(),
                linkTo(methodOn(StreetController.class).addresses(entity.getStreetId())).withRel("addresses"),
                linkTo(methodOn(StreetController.class).all()).withRel("addresses")
        );
    }
}
