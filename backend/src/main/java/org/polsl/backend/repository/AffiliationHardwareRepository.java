package org.polsl.backend.repository;

import org.polsl.backend.entity.AffiliationHardware;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AffiliationHardwareRepository extends CrudRepository<AffiliationHardware, Long> {
}
