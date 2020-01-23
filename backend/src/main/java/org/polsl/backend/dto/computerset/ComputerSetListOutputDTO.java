package org.polsl.backend.dto.computerset;

import org.polsl.backend.service.export.ExportColumn;

import java.util.Set;

public class ComputerSetListOutputDTO {
  private Long id;
  private Boolean deleted;

  @ExportColumn("Nazwa")
  private String name;

  @ExportColumn("Numer inwentarzowy")
  private String computerSetInventoryNumber;

  @ExportColumn("Przynależy do")
  private String affiliationName;

  @ExportColumn("Numery inwentarzowe powiązanych oprogramowań")
  private Set<String> softwareInventoryNumbers;

  @ExportColumn("Numery inwentarzowe powiązanych sprzętów")
  private Set<String> hardwareInventoryNumbers;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Boolean getDeleted() {
    return deleted;
  }

  public void setDeleted(Boolean deleted) {
    this.deleted = deleted;
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
