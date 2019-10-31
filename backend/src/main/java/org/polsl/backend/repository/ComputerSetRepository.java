package org.polsl.backend.repository;

import org.polsl.backend.entity.ComputerSet;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface ComputerSetRepository extends CrudRepository<ComputerSet, Long> {
    Optional<ComputerSet> findById(Long id);
    Optional<ComputerSet> findAllById(Long id);

}
