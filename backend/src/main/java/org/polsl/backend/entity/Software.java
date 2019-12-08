package org.polsl.backend.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * Klasa reprezentująca bazodanową encję oprogramowania.
 */
@Entity
@Table(name = "software")
public class Software {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private Long id;
  private String name;
  private String inventoryNumber;
  private String key;
  private Long availableKeys;
  private Long duration;
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime validTo;

  @OneToMany(mappedBy = "software")
  private Set<ComputerSetSoftware> computerSetSoftwareSet;

  public Software() {
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Set<ComputerSetSoftware> getComputerSetSoftwareSet() { return computerSetSoftwareSet; }

  public void setComputerSetSoftwareSet(Set<ComputerSetSoftware> computerSetSoftwareSet) { this.computerSetSoftwareSet = computerSetSoftwareSet; }

  public LocalDateTime getValidTo() { return validTo; }

  public void setValidTo(LocalDateTime validTo) { this.validTo = validTo; }

  public String getInventoryNumber() { return inventoryNumber; }

  public void setInventoryNumber(String inventoryNumber) { this.inventoryNumber = inventoryNumber; }

  public String getKey() { return key; }

  public void setKey(String key) { this.key = key; }

  public Long getAvailableKeys() { return availableKeys; }

  public void setAvailableKeys(Long availableKeys) { this.availableKeys = availableKeys; }

  public Long getDuration() { return duration; }
  public void setDuration(Long duration) { this.duration = duration; }
}
