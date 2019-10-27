package org.polsl.backend.dto.statistics;

public class StatisticsOutputDTO {
  private Long affiliationsCount;
  private Long computerSetsCount;
  private Long hardwareCount;
  private Long softwareCount;

  //region gettersAndSetters
  public Long getAffiliationsCount() {
    return affiliationsCount;
  }

  public void setAffiliationsCount(Long affiliationsCount) {
    this.affiliationsCount = affiliationsCount;
  }

  public Long getComputerSetsCount() {
    return computerSetsCount;
  }

  public void setComputerSetsCount(Long computerSetsCount) {
    this.computerSetsCount = computerSetsCount;
  }

  public Long getHardwareCount() {
    return hardwareCount;
  }

  public void setHardwareCount(Long hardwareCount) {
    this.hardwareCount = hardwareCount;
  }

  public Long getSoftwareCount() {
    return softwareCount;
  }

  public void setSoftwareCount(Long softwareCount) {
    this.softwareCount = softwareCount;
  }
  //endregion
}
