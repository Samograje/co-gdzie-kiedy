package org.polsl.backend.repository;

import org.polsl.backend.entity.ComputerSetSoftware;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface ComputerSetSoftwareRepository extends CrudRepository <ComputerSetSoftware, Long> {

    List<ComputerSetSoftware> findAllById(Long id);
    Set<ComputerSetSoftware> findAllBySoftwareId(Long id);
}
