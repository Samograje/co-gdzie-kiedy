package org.polsl.backend.repository;

import org.polsl.backend.entity.Affiliation;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AffiliationRepository extends CrudRepository<Affiliation, Long> {
}
