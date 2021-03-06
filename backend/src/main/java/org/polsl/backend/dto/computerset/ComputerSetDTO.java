package org.polsl.backend.dto.computerset;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Set;

public class ComputerSetDTO {
  @NotEmpty
  private String name;
  @NotNull
  private Long affiliationId;
  @NotNull
  private Set<Long> hardwareIds;
  @NotNull
  private Set<Long> softwareIds;

  private String inventoryNumber;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Long getAffiliationId() {
    return affiliationId;
  }

  public void setAffiliationId(Long affiliationId) {
    this.affiliationId = affiliationId;
  }

  public Set<Long> getHardwareIds() {
    return hardwareIds;
  }

  public void setHardwareIds(Set<Long> hardwareIds) {
    this.hardwareIds = hardwareIds;
  }

  public Set<Long> getSoftwareIds() {
    return softwareIds;
  }

  public void setSoftwareIds(Set<Long> softwareIds) {
    this.softwareIds = softwareIds;
  }

  public String getInventoryNumber() {
    return inventoryNumber;
  }

  public void setInventoryNumber(String inventoryNumber) {
    this.inventoryNumber = inventoryNumber;
  }
}
