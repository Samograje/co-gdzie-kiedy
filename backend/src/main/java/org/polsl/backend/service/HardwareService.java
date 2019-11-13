package org.polsl.backend.service;

import org.polsl.backend.dto.PaginatedResult;
import org.polsl.backend.dto.hardware.HardwareDTO;
import org.polsl.backend.dto.hardware.HardwareOutputDTO;
import org.polsl.backend.entity.Affiliation;
import org.polsl.backend.entity.AffiliationHardware;
import org.polsl.backend.entity.ComputerSet;
import org.polsl.backend.entity.ComputerSetHardware;
import org.polsl.backend.entity.Hardware;
import org.polsl.backend.entity.HardwareDictionary;
import org.polsl.backend.exception.NotFoundException;
import org.polsl.backend.repository.AffiliationHardwareRepository;
import org.polsl.backend.repository.AffiliationRepository;
import org.polsl.backend.repository.ComputerSetHardwareRepository;
import org.polsl.backend.repository.ComputerSetRepository;
import org.polsl.backend.repository.HardwareDictionaryRepository;
import org.polsl.backend.repository.HardwareRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
  public HardwareService(HardwareRepository hardwareRepository,
                         HardwareDictionaryRepository hardwareDictionaryRepository,
                         ComputerSetRepository computerSetRepository,
                         ComputerSetHardwareRepository computerSetHardwareRepository1,
                         AffiliationRepository affiliationRepository,
                         AffiliationHardwareRepository affiliationHardwareRepository) {
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

  public HardwareDTO getOneHardware(long id){
    Hardware hardware = hardwareRepository.findById(id)
        .orElseThrow(()->new NotFoundException("sprzęt","id",id));
    HardwareDTO dto = new HardwareDTO();
    dto.setName(hardware.getName());
    dto.setDictionaryId(hardware.getHardwareDictionary().getId());

    AffiliationHardware lastEntryAffiliation = affiliationHardwareRepository.findTheLatestRowForHardware(id)
        .orElseThrow(() -> new RuntimeException("Brak połączenia przynależności ze sprzętem o id: " + id));
    dto.setAffiliationId(lastEntryAffiliation.getAffiliation().getId());

    Optional<ComputerSetHardware> lastEntry = computerSetHardwareRepository.findTheLatestRowForHardware(id);
    if (lastEntry.isPresent()) {
      dto.setComputerSetId(lastEntry.get().getComputerSet().getId());
    } else {
      dto.setComputerSetId(null);
    }

    return dto;
  }

  @Transactional
  public void createHardware(HardwareDTO request) {
    Hardware hardware = new Hardware();
    hardware.setName(request.getName());
    HardwareDictionary hardwareDictionary = hardwareDictionaryRepository.findById(request.getDictionaryId())
        .orElseThrow(() -> new NotFoundException("słownik urządzeń", "id", request.getDictionaryId()));
    hardware.setHardwareDictionary(hardwareDictionary);
    hardwareRepository.save(hardware);

    if (request.getComputerSetId() != null) {
      ComputerSet computerSet = computerSetRepository.findByIdAndValidToIsNull(request.getComputerSetId())
          .orElseThrow(() -> new NotFoundException("zestaw komputerowy", "id", request.getComputerSetId()));
      ComputerSetHardware computerSetHardware = new ComputerSetHardware(computerSet, hardware);
      computerSetHardwareRepository.save(computerSetHardware);
    }

    Affiliation affiliation = affiliationRepository.findByIdAndIsDeletedIsFalse(request.getAffiliationId())
        .orElseThrow(() -> new NotFoundException("przynależność", "id", request.getAffiliationId()));
    AffiliationHardware affiliationHardware = new AffiliationHardware(affiliation, hardware);
    affiliationHardwareRepository.save(affiliationHardware);
  }

  @Transactional
  public void editHardware(Long id, HardwareDTO request) throws NotFoundException {
    Hardware hardware = hardwareRepository.findById(id)
        .orElseThrow(() -> new NotFoundException("sprzęt", "id", id));
    hardware.setName(request.getName());
    HardwareDictionary hardwareDictionary = hardwareDictionaryRepository.findById(request.getDictionaryId())
        .orElseThrow(() -> new NotFoundException("słownik urządzeń", "id", request.getDictionaryId()));
    hardware.setHardwareDictionary(hardwareDictionary);
    hardwareRepository.save(hardware);

    Optional<ComputerSetHardware> lastEntry = computerSetHardwareRepository.findTheLatestRowForHardware(id);
    if (lastEntry.isPresent() && !lastEntry.get().getComputerSet().getId().equals(request.getComputerSetId())) {
      ComputerSetHardware computerSetHardware = lastEntry.get();
      computerSetHardware.setValidTo(LocalDateTime.now());
      computerSetHardwareRepository.save(computerSetHardware);
    }
    if (request.getComputerSetId() != null && !(
        lastEntry.isPresent() && lastEntry.get().getComputerSet().getId().equals(request.getComputerSetId())
    )) {
      ComputerSet computerSet = computerSetRepository.findByIdAndValidToIsNull(request.getComputerSetId())
          .orElseThrow(() -> new NotFoundException("zestaw komputerowy", "id", request.getComputerSetId()));
      ComputerSetHardware computerSetHardware = new ComputerSetHardware(computerSet, hardware);
      computerSetHardwareRepository.save(computerSetHardware);
    }

    AffiliationHardware lastEntryAffiliation = affiliationHardwareRepository.findTheLatestRowForHardware(id)
        .orElseThrow(() -> new RuntimeException("Brak połączenia przynależności ze sprzętem o id: " + id));
    if (!lastEntryAffiliation.getAffiliation().getId().equals(request.getAffiliationId())) {
      lastEntryAffiliation.setValidTo(LocalDateTime.now());
      affiliationHardwareRepository.save(lastEntryAffiliation);
      Affiliation affiliation = affiliationRepository.findByIdAndIsDeletedIsFalse(request.getAffiliationId())
          .orElseThrow(() -> new NotFoundException("przynależność", "id", request.getAffiliationId()));
      AffiliationHardware affiliationHardware = new AffiliationHardware(affiliation, hardware);
      affiliationHardwareRepository.save(affiliationHardware);
    }
  }

  @Transactional
  public void deleteHardware(Long id) throws NotFoundException {
    Hardware hardware = hardwareRepository.findById(id)
        .orElseThrow(() -> new NotFoundException("sprzęt", "id", id));
    hardware.setValidTo(LocalDateTime.now());
    hardwareRepository.save(hardware);

    Optional<ComputerSetHardware> lastEntryComputerSet = computerSetHardwareRepository.findTheLatestRowForHardware(id);
    if (lastEntryComputerSet.isPresent()) {
      ComputerSetHardware computerSetHardware = lastEntryComputerSet.get();
      computerSetHardware.setValidTo(LocalDateTime.now());
      computerSetHardwareRepository.save(computerSetHardware);
    }

    AffiliationHardware lastEntryAffiliation = affiliationHardwareRepository.findTheLatestRowForHardware(id)
        .orElseThrow(() -> new RuntimeException("Brak połączenia przynależności ze sprzętem o id: " + id));
    lastEntryAffiliation.setValidTo(LocalDateTime.now());
    affiliationHardwareRepository.save(lastEntryAffiliation);
  }
}
