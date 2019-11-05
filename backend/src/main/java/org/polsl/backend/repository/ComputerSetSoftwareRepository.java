package org.polsl.backend.repository;

import org.polsl.backend.entity.ComputerSetSoftware;
import org.polsl.backend.key.ComputerSetSoftwareKey;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface ComputerSetSoftwareRepository extends CrudRepository<ComputerSetSoftware, ComputerSetSoftwareKey> {
  Set<ComputerSetSoftware> findAllBySoftwareIdAndValidToIsNull(Long id);
}
