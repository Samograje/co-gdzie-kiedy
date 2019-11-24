package org.polsl.backend.repository;

import org.polsl.backend.entity.AffiliationComputerSet;
import org.polsl.backend.key.AffiliationComputerSetKey;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AffiliationComputerSetRepository extends CrudRepository<AffiliationComputerSet, AffiliationComputerSetKey> {

  Iterable<AffiliationComputerSet> findAllByComputerSetIdAndValidToIsNull(Long computerSetId);

  AffiliationComputerSet findByAffiliationIdAndComputerSetIdAndValidToIsNull(Long affiliationId, Long computerSetId);
}
