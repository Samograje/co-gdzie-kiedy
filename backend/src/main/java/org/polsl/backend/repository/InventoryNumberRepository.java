package org.polsl.backend.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryNumberRepository extends CrudRepository {

  @Query(value = "SELECT LEFT(inventory_number,6) FROM computer_sets" +
                  "UNION" +
                  "SELECT LEFT(inventory_number,6) FROM hardware" +
                  "ORDER BY 1 DESC LIMIT 1;",
      nativeQuery = true)
  String findTheLatestInventoryNumber();
}
