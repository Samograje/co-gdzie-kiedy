package org.polsl.backend.repository;

import org.polsl.backend.entity.Hardware;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HardwareRepository extends JpaRepository<Hardware, Long>, JpaSpecificationExecutor<Hardware> {

  List<Hardware> findAllByComputerSetHardwareSetIsNullAndValidToIsNull();

  List<Hardware> findAllByValidToIsNull();

  Optional<Hardware> findByIdAndValidToIsNull(Long id);

  long countByValidToIsNull();

  long count();
}
