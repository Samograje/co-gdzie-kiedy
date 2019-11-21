package org.polsl.backend.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface WithInventoryNumber<T> extends CrudRepository<T, Long> {

//  @Query(value = "SELECT LEFT(inventory_number,6) FROM computer_sets" +
////                  "UNION" +
//                  "SELECT LEFT(inventory_number,6) FROM hardware" +
//      "union coś tam"+
//                  "ORDER BY 1 DESC LIMIT 1;",
//      nativeQuery = true)
//  String findTheLatestId();

  Long countAll();
}
