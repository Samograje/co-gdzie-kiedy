package org.polsl.inventory.repository;

import org.polsl.inventory.entity.ComputerSet;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComputerSetRepository extends CrudRepository<ComputerSet, Long> {
}
