package org.polsl.backend.repository;

import org.polsl.backend.entity.Hardware;
import org.polsl.backend.entity.HardwareDictionary;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HardwareRepository extends CrudRepository<Hardware, Long> {

  List<Hardware> findAllByComputerSetHardwareSetIsNull();

  Optional<Hardware> findByIdAndNameAndHardwareDictionary(Long id, String name, HardwareDictionary hardwareDictionary);
}
