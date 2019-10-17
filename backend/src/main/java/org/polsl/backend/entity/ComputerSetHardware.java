package org.polsl.backend.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="computer_sets_hardware")
public class ComputerSetHardware {
    @Id
    private Long id;

    @ManyToOne
    @JoinColumn(name = "computer_set_id")
    private ComputerSet computerSet;

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

    public ComputerSet getComputerSet() {
        return computerSet;
    }

    public void setComputerSet(ComputerSet computerSet) {
        this.computerSet = computerSet;
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
