package org.polsl.backend.dto.history;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class HistoryDTO {
  private String inventoryNumber;
  @NotNull
  private String name;
  @NotNull
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
  private LocalDateTime validFrom;
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
  private LocalDateTime validTo;

  public String getInventoryNumber() {
    return inventoryNumber;
  }

  public void setInventoryNumber(String inventoryNumber) {
    this.inventoryNumber = inventoryNumber;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public LocalDateTime getValidFrom() {
    return validFrom;
  }

  public void setValidFrom(LocalDateTime validFrom) {
    this.validFrom = validFrom;
  }

  public LocalDateTime getValidTo() {
    return validTo;
  }

  public void setValidTo(LocalDateTime validTo) {
    this.validTo = validTo;
  }
}
