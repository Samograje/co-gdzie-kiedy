package org.polsl.backend.dto.computerset;

import java.util.Set;

public class ComputerSetListOutputDTO {
  private Long id;
  private String name;
  private String computerSetInventoryNumber;
  private String affiliationName;
  private Set<String> softwareInventoryNumbers;
  private Set<String> hardwareInventoryNumbers;

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

  public String getComputerSetInventoryNumber() {
    return computerSetInventoryNumber;
  }

  public void setComputerSetInventoryNumber(String computerSetInventoryNumber) {
    this.computerSetInventoryNumber = computerSetInventoryNumber;
  }

  public String getAffiliationName() {
    return affiliationName;
  }

  public void setAffiliationName(String affiliationName) {
    this.affiliationName = affiliationName;
  }

  public Set<String> getSoftwareInventoryNumbers() {
    return softwareInventoryNumbers;
  }

  public void setSoftwareInventoryNumbers(Set<String> softwareInventoryNumbers) {
    this.softwareInventoryNumbers = softwareInventoryNumbers;
  }

  public Set<String> getHardwareInventoryNumbers() {
    return hardwareInventoryNumbers;
  }

  public void setHardwareInventoryNumbers(Set<String> hardwareInventoryNumbers) {
    this.hardwareInventoryNumbers = hardwareInventoryNumbers;
  }
}
