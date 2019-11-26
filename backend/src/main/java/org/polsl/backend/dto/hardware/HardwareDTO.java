package org.polsl.backend.dto.hardware;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class HardwareDTO {
  @NotEmpty
  private String name;

  @NotNull
  private Long dictionaryId;

  private Long computerSetId;

  @NotNull
  private Long affiliationId;

  private String inventoryNumber;


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Long getDictionaryId() {
    return dictionaryId;
  }

  public void setDictionaryId(Long dictionaryId) {
    this.dictionaryId = dictionaryId;
  }

  public Long getComputerSetId() {
    return computerSetId;
  }

  public void setComputerSetId(Long computerSetId) {
    this.computerSetId = computerSetId;
  }

  public Long getAffiliationId() {
    return affiliationId;
  }

  public void setAffiliationId(Long affiliationId) {
    this.affiliationId = affiliationId;
  }

  public String getInventoryNumber() {
    return inventoryNumber;
  }

  public void setInventoryNumber(String inventoryNumber) {
    this.inventoryNumber = inventoryNumber;
  }
}
