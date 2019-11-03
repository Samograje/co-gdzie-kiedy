package org.polsl.backend.repository;

import org.polsl.backend.entity.ComputerSet;
import org.polsl.backend.key.AffiliationComputerSetKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ComputerSetRepository extends JpaRepository<ComputerSet, AffiliationComputerSetKey> {
    Optional<ComputerSet> findById(Long id);
}
