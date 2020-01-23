package org.polsl.backend.dto.hardware;

import org.polsl.backend.service.export.ExportColumn;

public class HardwareListOutputDTO {
  private Long id;
  private Boolean deleted;

  @ExportColumn("Nazwa")
  private String name;

  @ExportColumn("Typ")
  private String type;

  @ExportColumn("Numer inwentarzowy")
  private String inventoryNumber;

  @ExportColumn("Przynależy do")
  private String affiliationName;

  @ExportColumn("Numer inwentarzowy powiązanego zestawu komputerowego")
  private String computerSetInventoryNumber;

  public HardwareListOutputDTO() {
  }

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

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getInventoryNumber() {
    return inventoryNumber;
  }

  public void setInventoryNumber(String inventoryNumber) {
    this.inventoryNumber = inventoryNumber;
  }

  public String getAffiliationName() {
    return affiliationName;
  }

  public void setAffiliationName(String affiliationName) {
    this.affiliationName = affiliationName;
  }

  public String getComputerSetInventoryNumber() {
    return computerSetInventoryNumber;
  }

  public void setComputerSetInventoryNumber(String computerSetInventoryNumber) {
    this.computerSetInventoryNumber = computerSetInventoryNumber;
  }
}
