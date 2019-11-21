package org.polsl.backend.service;

import org.polsl.backend.repository.ComputerSetRepository;
import org.polsl.backend.repository.HardwareRepository;
import org.polsl.backend.repository.SoftwareRepository;
import org.polsl.backend.repository.WithInventoryNumber;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class InventoryNumberService {

  String generateInventoryNumber(WithInventoryNumber repository) {
    // todo
    StringBuilder stringBuilder = new StringBuilder();
    if (repository instanceof HardwareRepository) {
      stringBuilder.append('H');
    } else if (repository instanceof SoftwareRepository) {
      stringBuilder.append('S');
    } else if (repository instanceof ComputerSetRepository){
      stringBuilder.append('C');
    }
    String latestInventoryNumber = repository.countAll().toString();
    int tempValue = Integer.getInteger(latestInventoryNumber) + 1;
    stringBuilder.append(tempValue);
    stringBuilder.append("/");
    stringBuilder.append(LocalDateTime.now().getYear());

    return stringBuilder.toString();
  }
}
