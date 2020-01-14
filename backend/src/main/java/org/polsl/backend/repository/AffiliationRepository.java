package org.polsl.backend.repository;

import org.polsl.backend.entity.Affiliation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AffiliationRepository extends JpaRepository<Affiliation, Long>, JpaSpecificationExecutor<Affiliation> {

  List<Affiliation> findAllByIsDeletedIsFalse();

  Optional<Affiliation> findByIdAndIsDeletedIsFalse(Long id);

  long countByIsDeletedIsFalse();
}
