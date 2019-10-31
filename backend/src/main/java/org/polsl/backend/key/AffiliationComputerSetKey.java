package org.polsl.backend.key;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Embeddable
public class AffiliationComputerSetKey implements Serializable {
  @Column(name = "affiliation_id")
  private Long affiliationId;

  @Column(name = "computer_set_id")
  private Long computerSetId;

  @Column(name = "valid_from")
  private LocalDateTime validFrom;

  public AffiliationComputerSetKey() {
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

  public Long getComputerSetId() {
    return computerSetId;
  }

  public void setComputerSetId(Long computerSetId) {
    this.computerSetId = computerSetId;
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
    AffiliationComputerSetKey affiliationComputerSetKey = (AffiliationComputerSetKey) o;
    return Objects.equals(affiliationId, affiliationComputerSetKey.affiliationId)
        && Objects.equals(computerSetId, affiliationComputerSetKey.computerSetId)
        && Objects.equals(validFrom, affiliationComputerSetKey.validFrom);
  }

  @Override
  public int hashCode() {
    return Objects.hash(affiliationId, computerSetId, validFrom);
  }
}
