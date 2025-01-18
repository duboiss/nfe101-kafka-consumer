package edu.cnam.nfe101.kafkaconsumer.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import edu.cnam.nfe101.kafkaconsumer.model.Address;

public interface AddressRepository extends JpaRepository<Address, Integer> {
    @EntityGraph(attributePaths = {"street"})
    Optional<Address> findById(Integer id);

    Optional<Address> findByCadastralParcel(String cadastralParcel);
}
