package org.polsl.backend.dto.software;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.Set;

public class SoftwareDTO {
  @NotEmpty
  private String name;
  @NotNull
  private Long availableKeys;
  @NotEmpty
  private String key;
  @NotNull
  private Long duration;

  private Long id;
  private String inventoryNumber;
  private Set<Long> computerSetIds;
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
  private LocalDateTime validTo;

  public String getName() { return name; }

  public void setName(String name) { this.name = name; }

  public Set<Long> getComputerSetIds() { return computerSetIds; }

  public void setComputerSetIds(Set<Long> computerSetIds) { this.computerSetIds = computerSetIds; }

  public Long getAvailableKeys() { return availableKeys; }

  public void setAvailableKeys(Long availableKeys) { this.availableKeys = availableKeys; }

  public String getKey() { return key; }

  public void setKey(String key) { this.key = key; }

  public Long getDuration() { return duration; }

  public void setDuration(Long duration) { this.duration = duration; }

  public String getInventoryNumber() {
    return inventoryNumber;
  }

  public void setInventoryNumber(String inventoryNumber) {
    this.inventoryNumber = inventoryNumber;
  }

  public Long getId() { return id; }

  public void setId(Long id) { this.id = id; }

  public LocalDateTime getValidTo() { return validTo; }

  public void setValidTo(LocalDateTime validTo) { this.validTo = validTo; }
}
