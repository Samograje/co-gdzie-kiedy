package org.polsl.backend.service;

import org.polsl.backend.dto.PaginatedResult;
import org.polsl.backend.dto.hardware.HardwareDTO;
import org.polsl.backend.dto.hardware.HardwareListOutputDTO;
import org.polsl.backend.entity.Affiliation;
import org.polsl.backend.entity.AffiliationHardware;
import org.polsl.backend.entity.ComputerSet;
import org.polsl.backend.entity.ComputerSetHardware;
import org.polsl.backend.entity.Hardware;
import org.polsl.backend.entity.HardwareDictionary;
import org.polsl.backend.exception.BadRequestException;
import org.polsl.backend.exception.NotFoundException;
import org.polsl.backend.repository.AffiliationHardwareRepository;
import org.polsl.backend.repository.AffiliationRepository;
import org.polsl.backend.repository.ComputerSetHardwareRepository;
import org.polsl.backend.repository.ComputerSetRepository;
import org.polsl.backend.repository.HardwareDictionaryRepository;
import org.polsl.backend.repository.HardwareRepository;
import org.polsl.backend.repository.InventoryNumberRepository;
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
  private final InventoryNumberService inventoryNumberService;

  @Autowired
  public HardwareService(HardwareRepository hardwareRepository,
                         HardwareDictionaryRepository hardwareDictionaryRepository,
                         ComputerSetRepository computerSetRepository,
                         ComputerSetHardwareRepository computerSetHardwareRepository1,
                         AffiliationRepository affiliationRepository,
                         AffiliationHardwareRepository affiliationHardwareRepository,
                         InventoryNumberService inventoryNumberService) {
    this.hardwareRepository = hardwareRepository;
    this.hardwareDictionaryRepository = hardwareDictionaryRepository;
    this.computerSetRepository = computerSetRepository;
    this.computerSetHardwareRepository = computerSetHardwareRepository1;
    this.affiliationRepository = affiliationRepository;
    this.affiliationHardwareRepository = affiliationHardwareRepository;
    this.inventoryNumberService = inventoryNumberService;
  }

  public PaginatedResult<HardwareListOutputDTO> getHardwareList(boolean soloOnly) {
    Iterable<Hardware> hardwareList;

    if(soloOnly){
      hardwareList = hardwareRepository.findAllByComputerSetHardwareSetIsNullAndValidToIsNull();
    }else {
      hardwareList = hardwareRepository.findAllByValidToIsNull();
    }

    List<HardwareListOutputDTO> dtos = new ArrayList<>();
    for (Hardware hardware : hardwareList) {
      HardwareListOutputDTO dto = new HardwareListOutputDTO();
      dto.setId(hardware.getId());
      dto.setName(hardware.getName());
      dto.setType(hardware.getHardwareDictionary().getValue());
      dto.setInventoryNumber(hardware.getInventoryNumber());

      AffiliationHardware hardwareAffiliation = affiliationHardwareRepository.findTheLatestRowForHardware(hardware.getId())
          .orElseThrow(() -> new RuntimeException("Brak połączenia przynależności ze sprzętem o id: " + hardware.getId()));
      dto.setAffiliationName(AffiliationService.generateName(hardwareAffiliation.getAffiliation()));

      Optional<ComputerSetHardware> lastEntry = computerSetHardwareRepository.findTheLatestRowForHardware(hardware.getId());
      lastEntry.ifPresent(computerSetHardware ->dto.setComputerSetInventoryNumber(computerSetHardware.getComputerSet().getInventoryNumber()));

      dtos.add(dto);
    }

    PaginatedResult<HardwareListOutputDTO> response = new PaginatedResult<>();
    response.setItems(dtos);
    response.setTotalElements((long) dtos.size());
    return response;
  }

  public HardwareDTO getOneHardware(long id) {
    Hardware hardware = hardwareRepository.findByIdAndValidToIsNull(id)
        .orElseThrow(() -> new NotFoundException("sprzęt", "id", id));
    HardwareDTO dto = new HardwareDTO();
    dto.setName(hardware.getName());
    dto.setInventoryNumber(hardware.getInventoryNumber());
    dto.setDictionaryId(hardware.getHardwareDictionary().getId());

    AffiliationHardware lastEntryAffiliation = affiliationHardwareRepository.findTheLatestRowForHardware(id)
        .orElseThrow(() -> new RuntimeException("Brak połączenia przynależności ze sprzętem o id: " + id));
    dto.setAffiliationId(lastEntryAffiliation.getAffiliation().getId());

    Optional<ComputerSetHardware> lastEntry = computerSetHardwareRepository.findTheLatestRowForHardware(id);
    lastEntry.ifPresent(computerSetHardware -> dto.setComputerSetId(computerSetHardware.getComputerSet().getId()));

    return dto;
  }

  @Transactional
  public void createHardware(HardwareDTO request) {
    if(request.getInventoryNumber() != null) throw new BadRequestException("Zakaz ręcznego wprowadzania numeru inwentarzowego.");

    Hardware hardware = new Hardware();

    hardware.setInventoryNumber(inventoryNumberService.generateInventoryNumber());
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
    if(request.getInventoryNumber() != null) throw new BadRequestException("Zakaz edycji numeru inwentarzowego.");

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
