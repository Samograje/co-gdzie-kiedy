package org.polsl.backend.repository;

import org.polsl.backend.entity.ComputerSetHardware;
import org.polsl.backend.key.ComputerSetHardwareKey;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ComputerSetHardwareRepository extends CrudRepository<ComputerSetHardware, ComputerSetHardwareKey> {

  @Query(value = "SELECT * FROM public.computer_sets_hardware WHERE hardware_id = :id ORDER BY valid_from desc LIMIT 1;",
      nativeQuery = true)
  Optional<ComputerSetHardware> findNewestRowForHardware(Long id);
}
