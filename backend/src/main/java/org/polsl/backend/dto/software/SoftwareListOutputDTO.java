package org.polsl.backend.dto.software;

import java.time.LocalDateTime;

public class SoftwareListOutputDTO {
  private Long id;
  private String name;
  private String inventoryNumber;
  private String key;
  private Long availableKeys;
  private LocalDateTime activeFrom;
  private LocalDateTime  validUntil;
  private LocalDateTime validTo;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getInventoryNumber() { return inventoryNumber; }

  public void setInventoryNumber(String inventoryNumber) { this.inventoryNumber = inventoryNumber; }

  public String getKey() { return key; }

  public void setKey(String key) { this.key = key; }

  public Long getAvailableKeys() { return availableKeys; }

  public void setAvailableKeys(Long availableKeys) { this.availableKeys = availableKeys; }

  public LocalDateTime getActiveFrom() { return activeFrom; }

  public void setActiveFrom(LocalDateTime activeFrom) { this.activeFrom = activeFrom; }

  public LocalDateTime getValidUntil() { return validUntil; }

  public void setValidUntil(LocalDateTime validUntil) { this.validUntil = validUntil; }

  public LocalDateTime getValidTo() { return validTo; }

  public void setValidTo(LocalDateTime validTo) { this.validTo = validTo; }
}
