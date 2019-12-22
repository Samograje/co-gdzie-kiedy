package org.polsl.backend.repository;

import org.polsl.backend.entity.ComputerSet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface ComputerSetRepository extends JpaRepository<ComputerSet, Long> {
  Optional<ComputerSet> findById(Long id);

  Set<ComputerSet> findAllByValidToIsNull();

  Optional<ComputerSet> findByIdAndValidToIsNull(Long id);

  long countByValidToIsNull();

  long count();
}
