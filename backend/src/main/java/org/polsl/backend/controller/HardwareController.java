package org.polsl.backend.controller;

import org.polsl.backend.service.HardwareService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Kontroler odpowiedzialny za zarządzanie sprzętem.
 */
@RestController
@RequestMapping("api/hardware")
public class HardwareController {
    private final HardwareService hardwareService;

    public HardwareController(HardwareService hardwareService){
        this.hardwareService = hardwareService;
    }

    /**
     * Endpoint obsługujący uzyskiwanie listy sprzętu.
     *
     * @return lista sprzętu
     */
    @GetMapping
    public ResponseEntity<?> getAllHardware(){ return ResponseEntity.ok(hardwareService.getAllHardwareAndComputerSets()); }
}
