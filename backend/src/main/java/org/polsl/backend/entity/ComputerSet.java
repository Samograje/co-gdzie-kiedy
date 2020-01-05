package org.polsl.backend.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Klasa reprezentująca bazodanową encję zestawu komputerowego.
 */
@Entity
@Table(name = "computer_sets")
public class ComputerSet {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @Column(name = "id")
  private Long id;

  private String name;

  @Column(name = "inventory_number")
  private String inventoryNumber;

  @Column(name = "valid_to")
  private LocalDateTime validTo;

  @OneToMany(mappedBy = "computerSet")
  private List<AffiliationComputerSet> affiliationComputerSetSet;

  @OneToMany(mappedBy = "computerSet")
  private List<ComputerSetHardware> computerSetHardwareSet;

  @OneToMany(mappedBy = "computerSet")
  private List<ComputerSetSoftware> computerSetSoftwareSet;

  public ComputerSet() {
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

  public String getInventoryNumber() {
    return inventoryNumber;
  }

  public void setInventoryNumber(String inventoryNumber) {
    this.inventoryNumber = inventoryNumber;
  }

  public List<AffiliationComputerSet> getAffiliationComputerSetSet() {
    return affiliationComputerSetSet;
  }

  public void setAffiliationComputerSetSet(List<AffiliationComputerSet> affiliationComputerSetSet) {
    this.affiliationComputerSetSet = affiliationComputerSetSet;
  }

  public List<ComputerSetHardware> getComputerSetHardwareSet() {
    return computerSetHardwareSet;
  }

  public void setComputerSetHardwareSet(List<ComputerSetHardware> computerSetHardwareSet) {
    this.computerSetHardwareSet = computerSetHardwareSet;
  }

  public List<ComputerSetSoftware> getComputerSetSoftwareSet() {
    return computerSetSoftwareSet;
  }

  public void setComputerSetSoftwareSet(List<ComputerSetSoftware> computerSetSoftwareSet) {
    this.computerSetSoftwareSet = computerSetSoftwareSet;
  }

  public LocalDateTime getValidTo() {
    return validTo;
  }

  public void setValidTo(LocalDateTime validTo) {
    this.validTo = validTo;
  }
}
