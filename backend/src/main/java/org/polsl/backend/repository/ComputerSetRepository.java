package org.polsl.backend.repository;

import org.polsl.backend.entity.ComputerSet;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComputerSetRepository extends CrudRepository<ComputerSet, Long> {
}
