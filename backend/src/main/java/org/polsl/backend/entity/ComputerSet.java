package org.polsl.backend.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.HashSet;
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

  public Set<AffiliationComputerSet> getValidAffiliationComputerSetSet() {
    if (affiliationComputerSetSet != null) {
      Set<AffiliationComputerSet> validAffiliationComputerSetSet = affiliationComputerSetSet;
      validAffiliationComputerSetSet.removeIf(validAffiliationComputerSet ->
              validAffiliationComputerSet.getValidTo() == null);
      return validAffiliationComputerSetSet;
    } else {
      return null;
    }
  }

  public Long getCurrentAffiliationId() {

    for (AffiliationComputerSet affiliationComputerSet : affiliationComputerSetSet) {
      if (affiliationComputerSet.getValidTo() == null) {
        return (affiliationComputerSet.getAffiliation()).getId();
      }
    }
      return null;
  }

  public Set<Long> getCurrentHardwareIds() {
    Set<Long> ids = new HashSet<>();

    computerSetHardwareSet.forEach(computerSetHardware -> {
      if (computerSetHardware.getValidTo() == null) {
        ids.add(computerSetHardware.getHardware().getId());
      }
    });

    return ids;
  }

  public Set<Long> getCurrentSoftwareIds() {
    Set<Long> ids = new HashSet<>();

    computerSetSoftwareSet.forEach(computerSetSoftware -> {
      if (computerSetSoftware.getValidTo() == null) {
        ids.add(computerSetSoftware.getSoftware().getId());
      }
    });

    return ids;
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
