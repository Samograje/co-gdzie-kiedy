package org.polsl.inventory.repository;

import org.polsl.inventory.entity.Affiliation;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AffiliationRepository extends CrudRepository<Affiliation, Long> {
}
