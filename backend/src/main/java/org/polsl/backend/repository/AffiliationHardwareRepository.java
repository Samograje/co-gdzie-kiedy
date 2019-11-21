package org.polsl.backend.repository;

import org.polsl.backend.entity.AffiliationHardware;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AffiliationHardwareRepository extends CrudRepository<AffiliationHardware, Long> {

  @Query(value = "SELECT * FROM affiliations_hardware WHERE hardware_id = :id AND valid_to IS NULL;",
      nativeQuery = true)
  Optional<AffiliationHardware> findTheLatestRowForHardware(Long id);
}
