package org.polsl.backend.repository;

import org.polsl.backend.entity.Affiliation;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AffiliationRepository extends CrudRepository<Affiliation, Long> {

  List<Affiliation> findAllByIsDeletedIsFalse();

  Optional<Affiliation> findByIdAndIsDeletedIsFalse(Long id);
}
