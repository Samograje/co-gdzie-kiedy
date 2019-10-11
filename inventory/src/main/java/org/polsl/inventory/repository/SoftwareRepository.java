package org.polsl.inventory.repository;


import org.polsl.inventory.entity.Software;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SoftwareRepository extends CrudRepository<Software, Long> {
}
