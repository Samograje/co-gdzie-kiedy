package org.polsl.backend.repository;

import org.polsl.backend.entity.AffiliationComputerSet;
import org.polsl.backend.key.AffiliationComputerSetKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AffiliationComputerSetRepository extends JpaRepository<AffiliationComputerSet, AffiliationComputerSetKey> {
}
