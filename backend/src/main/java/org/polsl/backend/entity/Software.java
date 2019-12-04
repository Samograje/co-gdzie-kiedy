package org.polsl.backend.entity;

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
  private LocalDateTime activeFrom;
  private LocalDateTime  activeTo;
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

  public LocalDateTime getActiveTo() { return activeTo; }

  public void setActiveTo(LocalDateTime activeTo) { this.activeTo = activeTo; }

  public String getInventoryNumber() { return inventoryNumber; }

  public void setInventoryNumber(String inventoryNumber) { this.inventoryNumber = inventoryNumber; }

  public String getKey() { return key; }

  public void setKey(String key) { this.key = key; }

  public Long getAvailableKeys() { return availableKeys; }

  public void setAvailableKeys(Long availableKeys) { this.availableKeys = availableKeys; }

  public LocalDateTime getActiveFrom() { return activeFrom; }

  public void setActiveFrom(LocalDateTime activeFrom) { this.activeFrom = activeFrom; }
}
