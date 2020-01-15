package org.polsl.backend.service;

import org.polsl.backend.dto.PaginatedResult;
import org.polsl.backend.dto.hardware.HardwareDTO;
import org.polsl.backend.dto.hardware.HardwareListOutputDTO;
import org.polsl.backend.dto.history.HistoryDTO;
import org.polsl.backend.entity.Affiliation;
import org.polsl.backend.entity.AffiliationHardware;
import org.polsl.backend.entity.ComputerSet;
import org.polsl.backend.entity.ComputerSetHardware;
import org.polsl.backend.entity.Hardware;
import org.polsl.backend.entity.HardwareDictionary;
import org.polsl.backend.entity.Software;
import org.polsl.backend.exception.BadRequestException;
import org.polsl.backend.exception.NotFoundException;
import org.polsl.backend.repository.AffiliationHardwareRepository;
import org.polsl.backend.repository.AffiliationRepository;
import org.polsl.backend.repository.ComputerSetHardwareRepository;
import org.polsl.backend.repository.ComputerSetRepository;
import org.polsl.backend.repository.HardwareDictionaryRepository;
import org.polsl.backend.repository.HardwareRepository;
import org.polsl.backend.type.InventoryNumberEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
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

  //region HTTP METHODS
  public PaginatedResult<HardwareListOutputDTO> getHardwareList(boolean soloOnly, Specification<Hardware> spec) {
    Iterable<Hardware> hardwareList;

    Specification<Hardware> specificationWithValidTo = (
        (Specification<Hardware>) (root, query, criteriaBuilder) -> criteriaBuilder.isNull(root.get("validTo"))
    ).and(spec);

    if (soloOnly) {
      Specification<Hardware> specificationWithSoloOnly = (
          (Specification<Hardware>) (root, query, criteriaBuilder) -> criteriaBuilder.isNull(root.get("computerSetHardwareSet"))
      ).and(specificationWithValidTo);
      hardwareList = hardwareRepository.findAll(specificationWithSoloOnly);
    } else {
      hardwareList = hardwareRepository.findAll(specificationWithValidTo);
    }


    List<HardwareListOutputDTO> dtos = new ArrayList<>();
    for (Hardware hardware : hardwareList) {
      HardwareListOutputDTO dto = new HardwareListOutputDTO();
      dto.setId(hardware.getId());
      dto.setName(hardware.getName());
      dto.setType(hardware.getHardwareDictionary().getValue());
      dto.setInventoryNumber(hardware.getInventoryNumber());
      dto.setAffiliationName(getValidAffiliationName(hardware));
      dto.setComputerSetInventoryNumber(getComputerSetInventoryNumber(hardware));
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

    //WCZYTAJ NAJNOWSZĄ PRZYNALEŻNOSC DO KTÓREJ NALEZY SPRZET
    AffiliationHardware lastEntryAffiliation = affiliationHardwareRepository.findTheLatestRowForHardware(id)
        .orElseThrow(() -> new RuntimeException("Brak połączenia przynależności ze sprzętem o id: " + id));
    dto.setAffiliationId(lastEntryAffiliation.getAffiliation().getId());

    Optional<ComputerSetHardware> lastEntry = computerSetHardwareRepository.findTheLatestRowForHardware(id);
    lastEntry.ifPresent(computerSetHardware -> dto.setComputerSetId(computerSetHardware.getComputerSet().getId()));

    return dto;
  }

  public PaginatedResult<HistoryDTO> getHardwareAffiliationsHistory(long id) {
    Iterable<AffiliationHardware> affiliationHardwareList;

    affiliationHardwareList = affiliationHardwareRepository.findAllByHardwareId(id);

    List<HistoryDTO> dtos = new ArrayList<>();
    for (AffiliationHardware affiliationHardware : affiliationHardwareList) {
      HistoryDTO dto = new HistoryDTO();
      dto.setName(AffiliationService.generateName(affiliationHardware.getAffiliation()));
      dto.setValidFrom(affiliationHardware.getValidFrom());
      if (affiliationHardware.getValidTo() != null) {
        dto.setValidTo(affiliationHardware.getValidTo());
      }
      dtos.add(dto);
    }

    PaginatedResult<HistoryDTO> response = new PaginatedResult<>();
    response.setItems(dtos);
    response.setTotalElements((long) dtos.size());
    return response;
  }

  public PaginatedResult<HistoryDTO> getHardwareComputerSetsHistory(long id) {
    Iterable<ComputerSetHardware> computerSetHardwareList;

    computerSetHardwareList = computerSetHardwareRepository.findAllByHardwareId(id);

    List<HistoryDTO> dtos = new ArrayList<>();
    for (ComputerSetHardware computerSetHardware : computerSetHardwareList) {
      HistoryDTO dto = new HistoryDTO();
      dto.setInventoryNumber(computerSetHardware.getComputerSet().getInventoryNumber());
      dto.setName(computerSetHardware.getComputerSet().getName());
      dto.setValidFrom(computerSetHardware.getValidFrom());
      if (computerSetHardware.getValidTo() != null) {
        dto.setValidTo(computerSetHardware.getValidTo());
      }
      dtos.add(dto);
    }

    PaginatedResult<HistoryDTO> response = new PaginatedResult<>();
    response.setItems(dtos);
    response.setTotalElements((long) dtos.size());
    return response;
  }

  @Transactional
  public void createHardware(HardwareDTO request) {
    if (request.getInventoryNumber() != null)
      throw new BadRequestException("Zakaz ręcznego wprowadzania numeru inwentarzowego.");

    Hardware hardware = new Hardware();

    //WYGENERUJ NUMER INWENTARZOWY
    String newInvNumb = InventoryNumberGenerator
        .generateInventoryNumber(InventoryNumberEnum.HARDWARE, hardwareRepository.count());
    hardware.setInventoryNumber(newInvNumb);

    hardware.setName(request.getName());
    HardwareDictionary hardwareDictionary = hardwareDictionaryRepository.findById(request.getDictionaryId())
        .orElseThrow(() -> new NotFoundException("słownik urządzeń", "id", request.getDictionaryId()));
    hardware.setHardwareDictionary(hardwareDictionary);
    hardwareRepository.save(hardware);

    //STWORZ POLACZENIE Z ZESTAWEM KOMPUTEROWYM
    if (request.getComputerSetId() != null) {
      ComputerSet computerSet = computerSetRepository.findByIdAndValidToIsNull(request.getComputerSetId())
          .orElseThrow(() -> new NotFoundException("zestaw komputerowy", "id", request.getComputerSetId()));
      ComputerSetHardware computerSetHardware = new ComputerSetHardware(computerSet, hardware);
      computerSetHardwareRepository.save(computerSetHardware);
    }

    //STWORZ POLACZENIE Z PRZYNALEZNOSCIA
    Affiliation affiliation = affiliationRepository.findByIdAndIsDeletedIsFalse(request.getAffiliationId())
        .orElseThrow(() -> new NotFoundException("przynależność", "id", request.getAffiliationId()));
    AffiliationHardware affiliationHardware = new AffiliationHardware(affiliation, hardware);
    affiliationHardwareRepository.save(affiliationHardware);
  }

  @Transactional
  public void editHardware(Long id, HardwareDTO request) throws NotFoundException {
    if (request.getInventoryNumber() != null) throw new BadRequestException("Zakaz edycji numeru inwentarzowego.");

    //WCZYTAJ Z BAZY SPRZET, KTORY BEDZIE EDYTOWANY
    Hardware hardware = hardwareRepository.findById(id)
        .orElseThrow(() -> new NotFoundException("sprzęt", "id", id));
    hardware.setName(request.getName());
    HardwareDictionary hardwareDictionary = hardwareDictionaryRepository.findById(request.getDictionaryId())
        .orElseThrow(() -> new NotFoundException("słownik urządzeń", "id", request.getDictionaryId()));
    hardware.setHardwareDictionary(hardwareDictionary);
    hardwareRepository.save(hardware);

    //SPRAWDZ CZY AKTUALNY STAN SPRZETU POWIAZANEGO Z DANYM ZESTAWEM KOMPUETROWYM MA BYC ZMIENIONY
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

    //SPRAWCZY CZY AKTUALNY STAN PRZYNALEZNOSCI SPRZETU MA BYC ZMIENIONY
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

    //ZAKONCZ POWIAZANIE Z ZESTAWEM KOMPUTEROWYM
    Optional<ComputerSetHardware> lastEntryComputerSet = computerSetHardwareRepository.findTheLatestRowForHardware(id);
    if (lastEntryComputerSet.isPresent()) {
      ComputerSetHardware computerSetHardware = lastEntryComputerSet.get();
      computerSetHardware.setValidTo(LocalDateTime.now());
      computerSetHardwareRepository.save(computerSetHardware);
    }

    //ZAKONCZ POWIAZANIE Z PRZYNALEZNOSCIA
    AffiliationHardware lastEntryAffiliation = affiliationHardwareRepository.findTheLatestRowForHardware(id)
        .orElseThrow(() -> new RuntimeException("Brak połączenia przynależności ze sprzętem o id: " + id));
    lastEntryAffiliation.setValidTo(LocalDateTime.now());
    affiliationHardwareRepository.save(lastEntryAffiliation);
  }
  //endregion

  //region METODY POMOCNICZE
  private String getValidAffiliationName(Hardware hardware) {
    for (AffiliationHardware affiliationHardware : hardware.getAffiliationHardwareSet()) {
      if (affiliationHardware.getValidTo() == null) {
        return AffiliationService.generateName(affiliationHardware.getAffiliation());
      }
    }
    return null;
  }

  private String getComputerSetInventoryNumber(Hardware hardware) {
    for (ComputerSetHardware computerSetHardware : hardware.getComputerSetHardwareSet()) {
      if (computerSetHardware.getValidTo() == null) {
        return computerSetHardware.getComputerSet().getInventoryNumber();
      }
    }
    return null;
  }
  //endregion
}

