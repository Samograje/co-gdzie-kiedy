package org.polsl.backend.repository;

import org.polsl.backend.entity.ComputerSetSoftware;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;


import java.util.Set;

@Repository
public interface ComputerSetSoftwareRepository extends CrudRepository <ComputerSetSoftware, Long> {

    Set<ComputerSetSoftware> findAllBySoftwareId(Long id);
}
