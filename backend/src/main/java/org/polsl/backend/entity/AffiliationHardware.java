package org.polsl.backend.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="affiliations_hardware")
public class AffiliationHardware {

    @Id
    private Long id;

    @ManyToOne
    @JoinColumn(name = "affiliation_id")
    private Affiliation affiliation;

    @ManyToOne
    @JoinColumn(name = "hardware_id")
    private Hardware hardware;

    private LocalDateTime validFrom;
    private LocalDateTime validTo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Affiliation getAffiliation() {
        return affiliation;
    }

    public void setAffiliation(Affiliation affiliation) {
        this.affiliation = affiliation;
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
