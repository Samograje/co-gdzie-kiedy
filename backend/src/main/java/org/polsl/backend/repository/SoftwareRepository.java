package org.polsl.backend.repository;


import org.polsl.backend.entity.Software;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface SoftwareRepository extends JpaRepository<Software, Long>, JpaSpecificationExecutor<Software> {
  Optional<Software> findById(Long id);
  Optional<Software> findByIdAndValidToIsNull(Long id);
  Iterable<Software> findAllByValidToIsNull();
  long countByValidToIsNull();
  long count();
}
