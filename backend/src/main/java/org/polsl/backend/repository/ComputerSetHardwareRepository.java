package org.polsl.backend.repository;

import org.polsl.backend.entity.ComputerSetHardware;
import org.polsl.backend.key.ComputerSetHardwareKey;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface ComputerSetHardwareRepository extends CrudRepository<ComputerSetHardware, ComputerSetHardwareKey> {

  @Query(value = "SELECT * FROM computer_sets_hardware WHERE hardware_id = :id AND valid_to IS NULL;",
      nativeQuery = true)
  Optional<ComputerSetHardware> findTheLatestRowForHardware(Long id);

  List<ComputerSetHardware> findAllByHardwareId(Long id);

  Set<ComputerSetHardware> findAllByComputerSetId(Long id);

  Set<ComputerSetHardware> findAllByComputerSetIdAndValidToIsNull(Long computerSetId);

  ComputerSetHardware findByComputerSetIdAndHardwareIdAndValidToIsNull(Long computerSetId, Long hardwareId);

  Set<ComputerSetHardware> findByComputerSetIdAndValidToIsNull(Long computerSetId);
}
