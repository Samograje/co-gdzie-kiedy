package org.polsl.backend.dto.hardware;

public class HardwareListOutputDTO {
  private Long id;
  private String name;
  private String type;
  private String inventoryNumber;
  private String affiliationName;
  private String computerSetInventoryNumber;

  public HardwareListOutputDTO() {
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
