package org.polsl.backend.entity;

import javax.persistence.*;
import java.util.Set;

/**
 *
 */
@Entity
@Table(name = "computer_sets")
public class ComputerSet {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String name;

    @ManyToMany(mappedBy = "computerSets")
    private Set<Affiliation> affiliations;

    @ManyToMany
    @JoinTable(name = "computer_sets_hardware",
            joinColumns = @JoinColumn(name = "computer_set_id"),
            inverseJoinColumns = @JoinColumn(name = "hardware_id"))
    private Set<Hardware> hardwares;

    public ComputerSet() {
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

    public Set<Affiliation> getAffiliations() {
        return affiliations;
    }

    public void setAffiliations(Set<Affiliation> affiliations) {
        this.affiliations = affiliations;
    }

    public Set<Hardware> getHardwares() {
        return hardwares;
    }

    public void setHardwares(Set<Hardware> hardwares) {
        this.hardwares = hardwares;
    }
}
