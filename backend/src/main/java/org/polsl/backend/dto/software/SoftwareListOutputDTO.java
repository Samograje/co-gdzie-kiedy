package org.polsl.backend.dto.software;

import java.util.Set;

public class SoftwareListOutputDTO {
  private Long id;
  private String name;
  private String inventoryNumber;
  private String key;
  private Long availableKeys;
  private Long duration;
  private Set<String> computerSetInventoryNumbers;

  public String getName() { return name; }

  public void setName(String name) { this.name = name; }

  public Long getId() { return id; }

  public void setId(Long id) { this.id = id; }

  public String getInventoryNumber() { return inventoryNumber; }

  public void setInventoryNumber(String inventoryNumber) { this.inventoryNumber = inventoryNumber; }

  public String getKey() { return key; }

  public void setKey(String key) { this.key = key; }

  public Long getAvailableKeys() { return availableKeys; }

  public void setAvailableKeys(Long availableKeys) { this.availableKeys = availableKeys; }

  public Long getDuration() { return duration; }

  public void setDuration(Long duration) { this.duration = duration; }

  public Set<String> getComputerSetInventoryNumbers() { return computerSetInventoryNumbers; }

  public void setComputerSetInventoryNumbers(Set<String> computerSetInventoryNumbers) {
    this.computerSetInventoryNumbers = computerSetInventoryNumbers;
  }
}
