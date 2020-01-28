package org.polsl.backend.dto.software;

import org.polsl.backend.service.export.ExportColumn;

import java.util.Set;

public class SoftwareListOutputDTO {
  private Long id;
  private Boolean deleted;

  @ExportColumn("Nazwa")
  private String name;

  @ExportColumn("Numer inwentarzowy")
  private String inventoryNumber;

  @ExportColumn("Klucz licencji")
  private String key;

  @ExportColumn("Ilość dostępnych kluczy")
  private Long availableKeys;

  @ExportColumn("Czas trwania")
  private Long duration;

  @ExportColumn("Numery inwentarzowe powiązanych zestawów komputerowych")
  private Set<String> computerSetInventoryNumbers;

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

  public String getInventoryNumber() {
    return inventoryNumber;
  }

  public void setInventoryNumber(String inventoryNumber) {
    this.inventoryNumber = inventoryNumber;
  }

  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public Long getAvailableKeys() {
    return availableKeys;
  }

  public void setAvailableKeys(Long availableKeys) {
    this.availableKeys = availableKeys;
  }

  public Long getDuration() {
    return duration;
  }

  public void setDuration(Long duration) {
    this.duration = duration;
  }

  public Set<String> getComputerSetInventoryNumbers() {
    return computerSetInventoryNumbers;
  }

  public void setComputerSetInventoryNumbers(Set<String> computerSetInventoryNumbers) {
    this.computerSetInventoryNumbers = computerSetInventoryNumbers;
  }
}
