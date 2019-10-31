package org.polsl.backend.repository;

import org.polsl.backend.entity.ComputerSetHardware;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComputerSetHardwareRepository extends CrudRepository<ComputerSetHardware, Long> {
}
