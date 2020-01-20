package org.polsl.backend.dto.affiliation;

import java.util.Set;

public class AffiliationListOutputDTO {
  private Long id;
  private String firstName;
  private String lastName;
  private String location;
  private Set<String> computerSetsInventoryNumbers;
  private Set<String> hardwareInventoryNumbers;

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

  public Set<String> getComputerSetsInventoryNumbers() {
    return computerSetsInventoryNumbers;
  }

  public void setComputerSetsInventoryNumbers(Set<String> computerSetsInventoryNumbers) {
    this.computerSetsInventoryNumbers = computerSetsInventoryNumbers;
  }

  public Set<String> getHardwareInventoryNumbers() {
    return hardwareInventoryNumbers;
  }

  public void setHardwareInventoryNumbers(Set<String> hardwareInventoryNumbers) {
    this.hardwareInventoryNumbers = hardwareInventoryNumbers;
  }
}
