package org.polsl.backend.repository;

import org.polsl.backend.entity.HardwareDictionary;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HardwareDictionaryRepository extends CrudRepository<HardwareDictionary, Long> {
}
