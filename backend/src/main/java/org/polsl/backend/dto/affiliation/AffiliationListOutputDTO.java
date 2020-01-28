package org.polsl.backend.dto.affiliation;

import org.polsl.backend.service.export.ExportColumn;

import java.util.Set;

public class AffiliationListOutputDTO {
  private Long id;
  private Boolean deleted;

  @ExportColumn("Imię")
  private String firstName;

  @ExportColumn("Nazwisko")
  private String lastName;

  @ExportColumn("Lokalizacja")
  private String location;

  @ExportColumn("Numery inwentarzowe powiązanych zestawów komputerowych")
  private Set<String> computerSetsInventoryNumbers;

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
