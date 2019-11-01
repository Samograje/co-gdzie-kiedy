package org.polsl.backend.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Set;

/**
 * Klasa reprezentująca bazodanową encję słownika hardware'u (jego typu).
 */
@Entity
@Table(name = "hardware_dictionary")
public class HardwareDictionary {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @Column(name = "id")
  private Long id;

  private String value;

  @OneToMany(mappedBy = "hardwareDictionary")
  private Set<Hardware> hardwareSet;

  public HardwareDictionary() {
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public Set<Hardware> getHardware() {
    return hardwareSet;
  }

  public void setHardware(Set<Hardware> hardwareSet) {
    this.hardwareSet = hardwareSet;
  }
}
