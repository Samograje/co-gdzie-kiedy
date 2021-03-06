package org.polsl.backend.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.Set;

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
  private Set<AffiliationComputerSet> affiliationComputerSetSet;

  @OneToMany(mappedBy = "computerSet")
  private Set<ComputerSetHardware> computerSetHardwareSet;

  @OneToMany(mappedBy = "computerSet")
  private Set<ComputerSetSoftware> computerSetSoftwareSet;

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

  public Set<AffiliationComputerSet> getAffiliationComputerSetSet() {
    return affiliationComputerSetSet;
  }

  public void setAffiliationComputerSetSet(Set<AffiliationComputerSet> affiliationComputerSetSet) {
    this.affiliationComputerSetSet = affiliationComputerSetSet;
  }

  public Set<ComputerSetHardware> getComputerSetHardwareSet() {
    return computerSetHardwareSet;
  }

  public void setComputerSetHardwareSet(Set<ComputerSetHardware> computerSetHardwareSet) {
    this.computerSetHardwareSet = computerSetHardwareSet;
  }

  public Set<ComputerSetSoftware> getComputerSetSoftwareSet() {
    return computerSetSoftwareSet;
  }

  public void setComputerSetSoftwareSet(Set<ComputerSetSoftware> computerSetSoftwareSet) {
    this.computerSetSoftwareSet = computerSetSoftwareSet;
  }

  public LocalDateTime getValidTo() {
    return validTo;
  }

  public void setValidTo(LocalDateTime validTo) {
    this.validTo = validTo;
  }
}
