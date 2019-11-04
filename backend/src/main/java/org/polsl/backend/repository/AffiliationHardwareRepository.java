package org.polsl.backend.repository;

import org.polsl.backend.entity.AffiliationHardware;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface AffiliationHardwareRepository extends CrudRepository<AffiliationHardware, Long> {
  Set<AffiliationHardware> findAllByHardwareId(Long id);

  @Query(value = "SELECT * FROM public.affiliations_hardware WHERE hardware_id = :id ORDER BY valid_from desc LIMIT 1;",
      nativeQuery = true)
  Optional<AffiliationHardware> findNewestRowForHardware(Long id);
}
