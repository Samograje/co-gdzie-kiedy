package org.polsl.inventory.repository;

import org.polsl.inventory.entity.HardwareDictionary;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HardwareDictionaryRepository extends CrudRepository<HardwareDictionary, Long> {
}
