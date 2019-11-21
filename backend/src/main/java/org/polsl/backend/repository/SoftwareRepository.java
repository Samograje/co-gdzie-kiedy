package org.polsl.backend.repository;


import org.polsl.backend.entity.Software;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SoftwareRepository extends CrudRepository<Software, Long> {
  Optional<Software> findById(Long id);
  Optional<Software> findByIdAndValidToIsNull(Long id);

  long countByValidToIsNull();

  long countAll();
}
