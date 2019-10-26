package org.polsl.backend.key;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Embeddable
public class SoftwareHardwareKey implements Serializable {
  @Column(name = "software_id")
  Long softwareId;
  @Column(name = "hardware_id")
  Long hardwareId;
  @Column(name = "valid_from")
  LocalDateTime validFrom;

  public SoftwareHardwareKey() {
  }

  //region gettersAndSetters

  public Long getSoftwareId() {
    return softwareId;
  }

  public void setSoftwareId(Long softwareId) {
    this.softwareId = softwareId;
  }

  public Long getHardwareId() {
    return hardwareId;
  }

  public void setHardwareId(Long hardwareId) {
    this.hardwareId = hardwareId;
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
    SoftwareHardwareKey softwareHardwareKey = (SoftwareHardwareKey) o;
    return Objects.equals(softwareId, softwareHardwareKey.softwareId)
            && Objects.equals(hardwareId, softwareHardwareKey.hardwareId)
            && Objects.equals(validFrom, softwareHardwareKey.validFrom);
  }

  @Override
  public int hashCode() {
    return Objects.hash(softwareId, hardwareId, validFrom);
  }
}
