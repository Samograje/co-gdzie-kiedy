package org.polsl.inventory.repository;

import org.polsl.inventory.entity.Hardware;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HardwareRepository extends CrudRepository<Hardware, Long> {
}
