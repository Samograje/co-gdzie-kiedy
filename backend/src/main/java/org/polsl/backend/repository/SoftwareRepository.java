package org.polsl.backend.repository;


import org.polsl.backend.entity.Software;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SoftwareRepository extends CrudRepository<Software, Long> {
}
