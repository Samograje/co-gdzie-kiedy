package org.polsl.backend.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 *
 */
@Entity
@Table(name = "hardware")
public class Hardware {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @Column(name = "id")
  private Long id;

  private String name;

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "hardware_dictionary_id", referencedColumnName = "id")
  @NotNull
  private HardwareDictionary hardwareDictionary;

  @OneToMany(mappedBy = "hardware")
  private Set<ComputerSetHardware> softwareHardwareSet;

  @OneToMany(mappedBy = "hardware")
  private Set<AffiliationHardware> affiliationHardwareSet;

  public Hardware() {
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public HardwareDictionary getHardwareDictionary() {
    return hardwareDictionary;
  }

  public void setHardwareDictionary(HardwareDictionary hardwareDictionary) {
    this.hardwareDictionary = hardwareDictionary;
  }

  public Set<ComputerSetHardware> getSoftwareHardwareSet() {
    return softwareHardwareSet;
  }

  public void setSoftwareHardwareSet(Set<ComputerSetHardware> softwareHardwareSet) {
    this.softwareHardwareSet = softwareHardwareSet;
  }

  public Set<AffiliationHardware> getAffiliationHardwareSet() {
    return affiliationHardwareSet;
  }

  public void setAffiliationHardwareSet(Set<AffiliationHardware> affiliationHardwareSet) {
    this.affiliationHardwareSet = affiliationHardwareSet;
  }
}
