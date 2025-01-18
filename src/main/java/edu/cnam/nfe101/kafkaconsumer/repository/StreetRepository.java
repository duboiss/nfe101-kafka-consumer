package edu.cnam.nfe101.kafkaconsumer.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.cnam.nfe101.kafkaconsumer.model.Street;

public interface StreetRepository extends JpaRepository<Street, Integer> {
    Optional<Street> findByName(String name);
}
