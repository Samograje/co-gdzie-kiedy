package org.polsl.backend.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.polsl.backend.key.ComputerSetSoftwareKey;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import java.time.LocalDateTime;

/**
 * Klasa reprezentująca bazodanową encję wpisu historii dotyczącego połączenia zestawu komputerowego z oprogramowaniem.
 */
@Entity
@Table(name = "computer_sets_software")
public class ComputerSetSoftware {
  @EmbeddedId
  private ComputerSetSoftwareKey id;

  @ManyToOne
  @MapsId("computer_set_id")
  @JoinColumn(name = "computer_set_id", insertable = false, updatable = false)
  private ComputerSet computerSet;

  @ManyToOne
  @MapsId("software_id")
  @JoinColumn(name = "software_id", insertable = false, updatable = false)
  private Software software;

  @Column(name = "valid_from", insertable = false, updatable = false)
  @MapsId("valid_from")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
  private LocalDateTime validFrom;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
  private LocalDateTime validTo;

  public ComputerSetSoftware() {
  }

  public ComputerSetSoftware(ComputerSet computerSet, Software software) {
    this.computerSet = computerSet;
    this.software = software;
    this.validFrom = LocalDateTime.now();
    this.id = new ComputerSetSoftwareKey(software.getId(), computerSet.getId(), this.validFrom);
  }

  public ComputerSetSoftwareKey getId() {
    return id;
  }

  public void setId(ComputerSetSoftwareKey id) {
    this.id = id;
  }

  public Software getSoftware() {
    return software;
  }

  public void setSoftware(Software software) {
    this.software = software;
  }

  public ComputerSet getComputerSet() {
    return computerSet;
  }

  public void setComputerSet(ComputerSet computerSet) {
    this.computerSet = computerSet;
  }

  public LocalDateTime getValidFrom() {
    return validFrom;
  }

  public void setValidFrom(LocalDateTime validFrom) {
    this.validFrom = validFrom;
  }

  public LocalDateTime getValidTo() {
    return validTo;
  }

  public void setValidTo(LocalDateTime validTo) {
    this.validTo = validTo;
  }
}
