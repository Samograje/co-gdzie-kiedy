package org.polsl.backend.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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

  @ManyToMany
  @JoinTable(name = "affiliations_hardware",
    joinColumns = @JoinColumn(name = "affiliation_id"),
    inverseJoinColumns = @JoinColumn(name = "hardware_id"))
  private Set<Hardware> hardware;

  @ManyToMany
  @JoinTable(name = "affiliations_computer_sets",
    joinColumns = @JoinColumn(name = "affiliation_id"),
    inverseJoinColumns = @JoinColumn(name = "computer_set_id"))
  private Set<ComputerSet> computerSets;

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

  public Set<Hardware> getHardware() {
    return hardware;
  }

  public void setHardware(Set<Hardware> hardware) {
    this.hardware = hardware;
  }

  public Set<ComputerSet> getComputerSets() {
    return computerSets;
  }

  public void setComputerSets(Set<ComputerSet> computerSets) {
    this.computerSets = computerSets;
  }
}
