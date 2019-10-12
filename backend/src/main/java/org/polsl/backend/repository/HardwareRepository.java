package org.polsl.backend.repository;

import org.polsl.backend.entity.Hardware;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HardwareRepository extends CrudRepository<Hardware, Long> {
}
