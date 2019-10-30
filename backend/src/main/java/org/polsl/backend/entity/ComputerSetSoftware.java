package org.polsl.backend.entity;

import org.polsl.backend.key.ComputerSetSoftwareKey;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name= "computer_sets_software")
public class ComputerSetSoftware {
    @EmbeddedId
    private ComputerSetSoftwareKey id;

    @ManyToMany
    @MapsId("computer_sets_id")
    @JoinColumn(name = "computer_sets_id", insertable = false, updatable = false)
    private ComputerSet computerSets;

    @ManyToMany
    @MapsId("software_id")
    @JoinColumn(name = "software_id", insertable = false, updatable = false)
    private Software software;

    @Column(name = "valid_from", insertable = false, updatable = false)
    private LocalDateTime validFrom;
    private LocalDateTime validTo;

    public ComputerSetSoftwareKey getId()  {return id; }

    public void setId(ComputerSetSoftwareKey id) { this.id = id;}

    public Software getSoftware(){ return software; }

    public void setSoftware(Software software) { this.software = software; }

    public ComputerSet getComputerSets(){ return computerSets; }

    public void setComputerSets(ComputerSet computerSet){ this.computerSets = computerSet; }

    public LocalDateTime getValidFrom() { return validFrom; }

    public void setValidFrom(LocalDateTime validFrom) { this.validFrom = validFrom; }

    public LocalDateTime getValidTo() { return validTo; }

    public void setValidTo(LocalDateTime validTo){ this.validTo = validTo; }


}
