package org.polsl.backend.service;

import org.polsl.backend.type.InventoryNumberEnum;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class InventoryNumberService {

  String generateInventoryNumber(InventoryNumberEnum type, Long size) {
    StringBuilder stringBuilder = new StringBuilder();
    switch (type) {
      case HARDWARE:
        stringBuilder.append('H');
        break;
      case SOFTWARE:
        stringBuilder.append('S');
        break;
      case COMPUTER_SET:
        stringBuilder.append('C');
        break;
    }

    long tempValue = size + 1;
    stringBuilder.append(tempValue);
    stringBuilder.append("/");
    stringBuilder.append(LocalDateTime.now().getYear());
    return stringBuilder.toString();
  }
}
