package org.polsl.backend.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Set;

/**
 * Klasa reprezentująca bazodanową encję przynależności.
 */
@Entity
@Table(name = "affiliations")
public class Affiliation {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private Long id;

  private String firstName;

  private String lastName;

  private String location;

  @OneToMany(mappedBy = "affiliation")
  private Set<AffiliationHardware> affiliationHardwareSet;

  @OneToMany(mappedBy = "affiliation")
  private Set<AffiliationComputerSet> affiliationComputerSetSet;

  private Boolean isDeleted;

  public Affiliation() {
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  public Boolean getDeleted() {
    return isDeleted;
  }

  public void setDeleted(Boolean deleted) {
    isDeleted = deleted;
  }

  public Set<AffiliationHardware> getAffiliationHardwareSet() {
    return affiliationHardwareSet;
  }

  public void setAffiliationHardwareSet(Set<AffiliationHardware> affiliationHardwareSet) {
    this.affiliationHardwareSet = affiliationHardwareSet;
  }

  public Set<AffiliationComputerSet> getAffiliationComputerSetSet() {
    return affiliationComputerSetSet;
  }

  public void setAffiliationComputerSetSet(Set<AffiliationComputerSet> affiliationComputerSetSet) {
    this.affiliationComputerSetSet = affiliationComputerSetSet;
  }
}
