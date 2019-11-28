package org.polsl.backend.dto.computersethardware;

public class ComputerSetHardwareHistoryDTO {
  private String computerSetInventoryNumber;
  private String computerSetName;
  private String validFrom;
  private String validTo;

  public ComputerSetHardwareHistoryDTO() {

  }

  public String getComputerSetInventoryNumber() {
    return computerSetInventoryNumber;
  }

  public void setComputerSetInventoryNumber(String computerSetInventoryNumber) {
    this.computerSetInventoryNumber = computerSetInventoryNumber;
  }

  public String getComputerSetName() {
    return computerSetName;
  }

  public void setComputerSetName(String computerSetName) {
    this.computerSetName = computerSetName;
  }

  public String getValidFrom() {
    return validFrom;
  }

  public void setValidFrom(String validFrom) {
    this.validFrom = validFrom;
  }

  public String getValidTo() {
    return validTo;
  }

  public void setValidTo(String validTo) {
    this.validTo = validTo;
  }
}
