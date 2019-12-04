package org.polsl.backend.dto.software;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class SoftwareListOutputDTO {
  private Long id;
  private String name;
  private String inventoryNumber;
  private String key;
  private Long availableKeys;
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private Timestamp duration;
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime validTo;

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


  public LocalDateTime getValidTo() { return validTo; }

  public void setValidTo(LocalDateTime validTo) { this.validTo = validTo; }

  public Timestamp getDuration() { return duration; }
  public void setDuration(Timestamp duration) { this.duration = duration; }
}
