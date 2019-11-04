package org.polsl.backend.repository;

import org.polsl.backend.entity.AffiliationHardware;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface AffiliationHardwareRepository extends CrudRepository<AffiliationHardware, Long> {
  Set<AffiliationHardware> findAllByHardwareId(Long id);

  //Optional<AffiliationHardware> findTopByHardwareIdByOrderByIdDesc(Long id);
}
