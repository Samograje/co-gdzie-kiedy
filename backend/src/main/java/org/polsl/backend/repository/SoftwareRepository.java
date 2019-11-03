package org.polsl.backend.repository;


import org.polsl.backend.entity.Software;
import org.polsl.backend.key.ComputerSetSoftwareKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SoftwareRepository extends JpaRepository<Software, ComputerSetSoftwareKey> {
  Optional<Software> findAllById(Long id);

  Optional<Software> findById(Long id);
}
