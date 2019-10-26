package org.polsl.backend.entity;

import org.polsl.backend.key.SoftwareHardwareKey;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "software_hardware")
public class SoftwareHardware {
  @EmbeddedId
  private SoftwareHardwareKey id;

  @ManyToOne
  @MapsId("software_id")
  @JoinColumn(name = "software_id", insertable = false, updatable = false)
  private Software software;

  @ManyToOne
  @MapsId("hardware_id")
  @JoinColumn(name = "hardware_id", insertable = false, updatable = false)
  private Hardware hardware;

  @Column(name = "valid_from", insertable = false, updatable = false)
  private LocalDateTime validFrom;
  private LocalDateTime validTo;

  public SoftwareHardwareKey getId() {
    return id;
  }

  public void setId(SoftwareHardwareKey id) {
    this.id = id;
  }

  public Software getSoftware() {
    return software;
  }

  public void setSoftware(Software software) {
    this.software = software;
  }

  public Hardware getHardware() {
    return hardware;
  }

  public void setHardware(Hardware hardware) {
    this.hardware = hardware;
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
