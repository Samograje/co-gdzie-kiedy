package org.polsl.backend.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.Set;

/**
 * Klasa reprezentująca bazodanową encję hardware'u.
 */
@Entity
@Table(name = "hardware")
public class Hardware {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @Column(name = "id")
  private Long id;

  private String name;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "hardware_dictionary_id", referencedColumnName = "id")
  private HardwareDictionary hardwareDictionary;

  @ManyToMany(mappedBy = "hardware")
  private Set<Affiliation> affiliations;

  @ManyToMany(mappedBy = "hardware")
  private Set<ComputerSet> computerSets;

  @OneToMany
  @JoinColumn(name = "software")
  private Set<Software> software;


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

  public Set<Affiliation> getAffiliations() {
    return affiliations;
  }

  public void setAffiliations(Set<Affiliation> affiliations) {
    this.affiliations = affiliations;
  }

  public Set<ComputerSet> getComputerSets() {
    return computerSets;
  }

  public void setComputerSets(Set<ComputerSet> computerSets) {
    this.computerSets = computerSets;
  }

  public Set<Software> getSoftware() {
    return software;
  }

  public void setSoftware(Set<Software> software) {
    this.software = software;
  }
}
