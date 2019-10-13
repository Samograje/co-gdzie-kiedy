package org.polsl.backend.entity;

import javax.persistence.*;
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

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "hardware_dictionary_id", referencedColumnName = "id")
    private HardwareDictionary hardwareDictionary;

    @ManyToMany(mappedBy = "hardware")
    private Set<Affiliation> affiliations;

    @ManyToMany(mappedBy = "hardware")
    private Set<ComputerSet> computerSets;


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
}
