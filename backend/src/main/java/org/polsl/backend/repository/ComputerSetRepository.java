package org.polsl.backend.repository;

import org.polsl.backend.entity.ComputerSet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComputerSetRepository extends JpaRepository<ComputerSet, Long> {
  long countByValidToIsNull();
}
