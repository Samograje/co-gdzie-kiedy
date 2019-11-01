package org.polsl.backend.key;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Klasa reprezentująca klucz główny encji {@link org.polsl.backend.entity.AffiliationHardware}
 */
@Embeddable
public class AffiliationHardwareKey implements Serializable {
  @Column(name = "affiliation_id")
  private Long affiliationId;

  @Column(name = "hardware_id")
  private Long hardwareId;

  @Column(name = "valid_from")
  private LocalDateTime validFrom;

  public AffiliationHardwareKey() {
  }

  //region gettersAndSetters

  public LocalDateTime getValidFrom() {
    return validFrom;
  }

  public void setValidFrom(LocalDateTime validFrom) {
    this.validFrom = validFrom;
  }

  public Long getAffiliationId() {
    return affiliationId;
  }

  public void setAffiliationId(Long affiliationId) {
    this.affiliationId = affiliationId;
  }

  public Long getHardwareId() {
    return hardwareId;
  }

  public void setHardwareId(Long hardwareId) {
    this.hardwareId = hardwareId;
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
    AffiliationHardwareKey affiliationHardwareKey = (AffiliationHardwareKey) o;
    return Objects.equals(affiliationId, affiliationHardwareKey.affiliationId)
        && Objects.equals(hardwareId, affiliationHardwareKey.hardwareId)
        && Objects.equals(validFrom, affiliationHardwareKey.validFrom);
  }

  @Override
  public int hashCode() {
    return Objects.hash(affiliationId, hardwareId, validFrom);
  }
}
