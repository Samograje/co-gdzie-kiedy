package org.polsl.backend.key;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Klasa reprezentująca klucz główny encji {@link org.polsl.backend.entity.ComputerSetHardware}
 */
@Embeddable
public class ComputerSetHardwareKey implements Serializable {
  @Column(name = "computer_set_id")
  private Long computerSetId;

  @Column(name = "hardware_id")
  private Long hardwareId;

  @Column(name = "valid_from")
  private LocalDateTime validFrom;

  public ComputerSetHardwareKey() {
  }

  //region gettersAndSetters

  public Long getComputerSetId() {
    return computerSetId;
  }

  public void setComputerSetId(Long computerSetId) {
    this.computerSetId = computerSetId;
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
    ComputerSetHardwareKey computerSetHardwareKey = (ComputerSetHardwareKey) o;
    return Objects.equals(computerSetId, computerSetHardwareKey.computerSetId)
        && Objects.equals(hardwareId, computerSetHardwareKey.hardwareId)
        && Objects.equals(validFrom, computerSetHardwareKey.validFrom);
  }

  @Override
  public int hashCode() {
    return Objects.hash(computerSetId, hardwareId, validFrom);
  }
}
