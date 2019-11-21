package org.polsl.backend.service;

import org.polsl.backend.repository.InventoryNumberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class InventoryNumberService {
  private final InventoryNumberRepository inventoryNumberRepository;

  @Autowired
  public InventoryNumberService(InventoryNumberRepository inventoryNumberRepository)  {
    this.inventoryNumberRepository = inventoryNumberRepository;
  }

  public String generateInventoryNumber(){
    String latestInventoryNumber = inventoryNumberRepository.findTheLatestInventoryNumber();
    StringBuilder stringBuilder = new StringBuilder();
    int tempValue = Integer.getInteger(latestInventoryNumber) + 1;
    stringBuilder.append(tempValue);
    stringBuilder.append("/");
    stringBuilder.append(LocalDateTime.now().getYear());

    return stringBuilder.toString();
  }
}
