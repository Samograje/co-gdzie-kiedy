package org.polsl.backend.service;

import org.polsl.backend.dto.PaginatedResult;
import org.polsl.backend.dto.hardware.HardwareInputDTO;
import org.polsl.backend.dto.hardware.HardwareOutputDTO;
import org.polsl.backend.entity.AffiliationHardware;
import org.polsl.backend.entity.ComputerSetHardware;
import org.polsl.backend.entity.Hardware;
import org.polsl.backend.entity.HardwareDictionary;
import org.polsl.backend.exception.NotFoundException;
import org.polsl.backend.key.AffiliationHardwareKey;
import org.polsl.backend.key.ComputerSetHardwareKey;
import org.polsl.backend.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Logika biznesowa hardware'u.
 */
@Service
public class HardwareService {
  private final HardwareRepository hardwareRepository;
  private final HardwareDictionaryRepository hardwareDictionaryRepository;
  private final ComputerSetRepository computerSetRepository;
  private final ComputerSetHardwareRepository computerSetHardwareRepository;
  private final AffiliationRepository affiliationRepository;
  private final AffiliationHardwareRepository affiliationHardwareRepository;

  @Autowired
  public HardwareService(HardwareRepository hardwareRepository, HardwareDictionaryRepository hardwareDictionaryRepository, ComputerSetRepository computerSetRepository, ComputerSetHardwareRepository computerSetHardwareRepository, ComputerSetHardwareRepository computerSetHardwareRepository1, AffiliationRepository affiliationRepository, AffiliationHardwareRepository affiliationHardwareRepository) {
    this.hardwareRepository = hardwareRepository;
    this.hardwareDictionaryRepository = hardwareDictionaryRepository;
    this.computerSetRepository = computerSetRepository;
    this.computerSetHardwareRepository = computerSetHardwareRepository1;
    this.affiliationRepository = affiliationRepository;
    this.affiliationHardwareRepository = affiliationHardwareRepository;
  }

  public PaginatedResult<HardwareOutputDTO> getAllSoloHardware() {
    Iterable<Hardware> soloHardware = hardwareRepository.findAllByComputerSetHardwareSetIsNull();
    List<HardwareOutputDTO> dtos = new ArrayList<>();
    for (Hardware hardware : soloHardware) {
      HardwareOutputDTO dto = new HardwareOutputDTO();
      dto.setId(hardware.getId());
      dto.setName(hardware.getName());
      dto.setType(hardware.getHardwareDictionary().getValue());
      dtos.add(dto);
    }
    PaginatedResult<HardwareOutputDTO> response = new PaginatedResult<>();
    response.setItems(dtos);
    response.setTotalElements((long) dtos.size());
    return response;
  }

  @Transactional
  public void createHardware(HardwareInputDTO request) {
    Hardware hardware = new Hardware();
    hardware.setName(request.getName());
    HardwareDictionary hardwareDictionary = hardwareDictionaryRepository.findById(request.getDictionaryId())
        .orElseThrow(() -> new NotFoundException("słownik urządzeń", "id", request.getDictionaryId()));
    hardware.setHardwareDictionary(hardwareDictionary);
    hardwareRepository.save(hardware);

    if(request.getComputerSetId() != null){
      ComputerSetHardwareKey computerSetHardwareKey = new ComputerSetHardwareKey();
      computerSetHardwareKey.setHardwareId(hardware.getId());
      computerSetHardwareKey.setComputerSetId(computerSetRepository.findById(request.getComputerSetId())
          .orElseThrow(() -> new NotFoundException("zestawy komputerowe", "id", request.getComputerSetId())).getId());
      computerSetHardwareKey.setValidFrom(LocalDateTime.now());
      ComputerSetHardware computerSetHardware = new ComputerSetHardware();
      computerSetHardware.setId(computerSetHardwareKey);
      computerSetHardware.setValidTo(null);
      computerSetHardwareRepository.save(computerSetHardware);
    }

    AffiliationHardwareKey affiliationHardwareKey = new AffiliationHardwareKey();
    affiliationHardwareKey.setHardwareId(hardware.getId());
    affiliationHardwareKey.setAffiliationId(affiliationRepository.findByIdAndIsDeletedIsFalse(request.getAffiliationId())
        .orElseThrow(() -> new NotFoundException("przynależność", "id", request.getAffiliationId())).getId());
    affiliationHardwareKey.setValidFrom(LocalDateTime.now());
    AffiliationHardware affiliationHardware = new AffiliationHardware();
    affiliationHardware.setId(affiliationHardwareKey);
    affiliationHardware.setValidTo(null);
    affiliationHardwareRepository.save(affiliationHardware);
  }
}
