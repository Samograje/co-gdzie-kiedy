package org.polsl.backend.repository;

import org.polsl.backend.entity.Hardware;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HardwareRepository extends CrudRepository<Hardware, Long> {

  @Query(
    value = "SELECT * FROM hardware h WHERE h.id NOT IN (SELECT csh.hardware_id FROM computer_sets_hardware csh);",
    nativeQuery = true
  )
  List<Hardware> findAllSoloHardware();
}
