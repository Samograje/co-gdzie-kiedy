package org.polsl.backend.key;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Embeddable
public class ComputerSetSoftwareKey implements Serializable {
  @Column(name = "software_id")
  private Long softwareId;

  @Column(name = "computer_set_id")
  private Long computerSetId;

  @Column(name = "valid_from")
  private LocalDateTime validFrom;

  public ComputerSetSoftwareKey() {
  }

  //region gettersAndSetters

  public Long getSoftwareId() {
    return softwareId;
  }

  public void setSoftwareId(Long softwareId) {
    this.softwareId = softwareId;
  }

  public Long getComputerSetId() {
    return computerSetId;
  }

  public void setComputerSetId(Long computerSetId) {
    this.computerSetId = computerSetId;
  }

  public LocalDateTime getValidFrom() {
    return validFrom;
  }

  public void setValidFrom(LocalDateTime validFrom) {
    this.validFrom = validFrom;
  }

  //endregion

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ComputerSetSoftwareKey computerSetSoftwareKey = (ComputerSetSoftwareKey) o;
    return Objects.equals(softwareId, computerSetSoftwareKey.softwareId)
        && Objects.equals(computerSetId, computerSetSoftwareKey.computerSetId)
        && Objects.equals(validFrom, computerSetSoftwareKey.validFrom);
  }

  @Override
  public int hashCode() {
    return Objects.hash(softwareId, computerSetId, validFrom);
  }
}
