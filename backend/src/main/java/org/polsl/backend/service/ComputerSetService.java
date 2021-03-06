package org.polsl.backend.service;

import org.polsl.backend.dto.PaginatedResult;
import org.polsl.backend.dto.computerset.ComputerSetDTO;
import org.polsl.backend.dto.computerset.ComputerSetListOutputDTO;
import org.polsl.backend.dto.history.HistoryDTO;
import org.polsl.backend.entity.Affiliation;
import org.polsl.backend.entity.AffiliationComputerSet;
import org.polsl.backend.entity.ComputerSet;
import org.polsl.backend.entity.ComputerSetHardware;
import org.polsl.backend.entity.ComputerSetSoftware;
import org.polsl.backend.entity.Hardware;
import org.polsl.backend.entity.Software;
import org.polsl.backend.exception.BadRequestException;
import org.polsl.backend.exception.NotFoundException;
import org.polsl.backend.repository.AffiliationComputerSetRepository;
import org.polsl.backend.repository.AffiliationRepository;
import org.polsl.backend.repository.ComputerSetHardwareRepository;
import org.polsl.backend.repository.ComputerSetRepository;
import org.polsl.backend.repository.ComputerSetSoftwareRepository;
import org.polsl.backend.repository.HardwareRepository;
import org.polsl.backend.repository.SoftwareRepository;
import org.polsl.backend.type.InventoryNumberEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Logika biznesowa zestawów komputerowych.
 */
@Service
public class ComputerSetService {
  private final ComputerSetRepository computerSetRepository;
  private final AffiliationComputerSetRepository affiliationComputerSetRepository;
  private final ComputerSetHardwareRepository computerSetHardwareRepository;
  private final ComputerSetSoftwareRepository computerSetSoftwareRepository;
  private final AffiliationRepository affiliationRepository;
  private final HardwareRepository hardwareRepository;
  private final SoftwareRepository softwareRepository;

  @Autowired
  public ComputerSetService(ComputerSetRepository computerSetRepository,
                            AffiliationComputerSetRepository affiliationComputerSetRepository,
                            ComputerSetHardwareRepository computerSetHardwareRepository,
                            ComputerSetSoftwareRepository computerSetSoftwareRepository,
                            AffiliationRepository affiliationRepository,
                            HardwareRepository hardwareRepository,
                            SoftwareRepository softwareRepository) {
    this.computerSetRepository = computerSetRepository;
    this.affiliationComputerSetRepository = affiliationComputerSetRepository;
    this.computerSetHardwareRepository = computerSetHardwareRepository;
    this.computerSetSoftwareRepository = computerSetSoftwareRepository;
    this.affiliationRepository = affiliationRepository;
    this.hardwareRepository = hardwareRepository;
    this.softwareRepository = softwareRepository;
  }

  public PaginatedResult<ComputerSetListOutputDTO> getAllComputerSets(Specification<ComputerSet> specification,
                                                                      boolean withDeleted) {
    final Specification<ComputerSet> resultSpecification = withDeleted
      ? specification
      : ((Specification<ComputerSet>) (root, query, criteriaBuilder) -> criteriaBuilder.isNull(root.get("validTo")))
      .and(specification);

    Iterable<ComputerSet> computerSets = computerSetRepository.findAll(resultSpecification);

    List<ComputerSetListOutputDTO> dtos = new ArrayList<>();
    for (ComputerSet computerSet : computerSets) {
      ComputerSetListOutputDTO dto = new ComputerSetListOutputDTO();
      dto.setId(computerSet.getId());
      dto.setDeleted(computerSet.getValidTo() != null);
      dto.setName(computerSet.getName());
      dto.setComputerSetInventoryNumber(computerSet.getInventoryNumber());
      dto.setAffiliationName(getValidAffiliationName(computerSet));
      dto.setHardwareInventoryNumbers(getValidHardwareInventoryNumbers(computerSet));
      dto.setSoftwareInventoryNumbers(getValidSoftwareInventoryNumbers(computerSet));
      dtos.add(dto);
    }
    PaginatedResult<ComputerSetListOutputDTO> response = new PaginatedResult<>();
    response.setItems(dtos);
    response.setTotalElements((long) dtos.size());
    return response;
  }

  @Transactional
  public void createComputerSet(ComputerSetDTO request) {

    ComputerSet computerSet = new ComputerSet();

    /* INVENTORY NUMBER */
    if (request.getInventoryNumber() != null)
      throw new BadRequestException("Zakaz ręcznego wprowadzania numeru inwentarzowego.");

    String newInvNumb = InventoryNumberGenerator
            .generateInventoryNumber(InventoryNumberEnum.COMPUTER_SET, computerSetRepository.count());
    computerSet.setInventoryNumber(newInvNumb);

    computerSet.setName(request.getName());
    computerSetRepository.save(computerSet);

    Affiliation affiliation = affiliationRepository.findByIdAndIsDeletedIsFalse(request.getAffiliationId())
            .orElseThrow(() -> new NotFoundException("przynależność", "id", request.getAffiliationId()));
    AffiliationComputerSet affiliationComputerSet = new AffiliationComputerSet(affiliation, computerSet);
    affiliationComputerSetRepository.save(affiliationComputerSet);

    if (request.getHardwareIds() != null) {
      request.getHardwareIds().forEach(hardwareId -> {
        Hardware hardware = hardwareRepository.findByIdAndValidToIsNull(hardwareId)
                .orElseThrow(() -> new NotFoundException("sprzęt", "id", hardwareId));
        ComputerSetHardware computerSetHardware = new ComputerSetHardware(computerSet, hardware);
        computerSetHardwareRepository.save(computerSetHardware);
      });
    }

    if (request.getSoftwareIds() != null) {
      request.getSoftwareIds().forEach(softwareId -> {
        Software software = softwareRepository.findByIdAndValidToIsNull(softwareId)
                .orElseThrow(() -> new NotFoundException("oprogramowanie", "id", softwareId));
        ComputerSetSoftware computerSetSoftware = new ComputerSetSoftware(computerSet, software);
        computerSetSoftwareRepository.save(computerSetSoftware);
      });
    }
  }

  @Transactional
  public void editComputerSet(Long id, ComputerSetDTO request) throws NotFoundException {
    ComputerSet computerSet = computerSetRepository.findByIdAndValidToIsNull(id)
            .orElseThrow(() -> new NotFoundException("zestaw komputerowy", "id", id));
    computerSet.setName(request.getName());
    computerSetRepository.save(computerSet);

    Long requestAffiliationId = request.getAffiliationId();
    Set<Long> requestHardwareIds = request.getHardwareIds();
    Set<Long> requestSoftwareIds = request.getSoftwareIds();

    Long currentAffiliationId = getCurrentAffiliationId(computerSet);
    Set<Long> currentHardwareIds = getCurrentHardwareIds(computerSet);
    Set<Long> currentSoftwareIds = getCurrentSoftwareIds(computerSet);

    //----------------------------------------INVENTORY NB----------------------------------------
    if (request.getInventoryNumber() != null) {
      throw new BadRequestException("Zakaz ręcznego wprowadzania numeru inwentarzowego.");
    }

    //----------------------------------------AFFILIATION----------------------------------------
    if (currentAffiliationId != requestAffiliationId) {
      //NOWE POŁĄCZENIE
      Affiliation newAffiliation = affiliationRepository.findByIdAndIsDeletedIsFalse(requestAffiliationId)
              .orElseThrow(() -> new NotFoundException("afiliacja", "id", requestAffiliationId));

      AffiliationComputerSet newAffiliationComputerSet = new AffiliationComputerSet(newAffiliation, computerSet);
      affiliationComputerSetRepository.save(newAffiliationComputerSet);
      //STARE POŁĄCZENIE
      AffiliationComputerSet oldAffiliationComputerSet = affiliationComputerSetRepository
              .findByAffiliationIdAndComputerSetIdAndValidToIsNull(currentAffiliationId, computerSet.getId());
      oldAffiliationComputerSet.setValidTo(LocalDateTime.now());
    }

    //----------------------------------------HARDWARE----------------------------------------
    //NOWE POŁĄCZENIE
    requestHardwareIds.forEach(requestHardwareId -> {
      if (!currentHardwareIds.contains(requestHardwareId)) {
        Hardware newHardware = hardwareRepository.findByIdAndValidToIsNull(requestHardwareId)
                .orElseThrow(() -> new NotFoundException("sprzęt", "id", requestHardwareId));

        ComputerSetHardware newComputerSetHardware = new ComputerSetHardware(computerSet, newHardware);
        computerSetHardwareRepository.save(newComputerSetHardware);
      }
    });
    //STARE POŁĄCZENIE
    currentHardwareIds.forEach(currentHardwareId -> {
      if (!requestHardwareIds.contains(currentHardwareId)) {
        ComputerSetHardware oldComputerSetHardware = computerSetHardwareRepository
                .findByComputerSetIdAndHardwareIdAndValidToIsNull(computerSet.getId(), currentHardwareId);
        oldComputerSetHardware.setValidTo(LocalDateTime.now());
      }
    });

    //----------------------------------------SOFTWARE----------------------------------------
    //NOWE POŁĄCZENIE
    requestSoftwareIds.forEach(requestSoftwareId -> {
      if (!currentSoftwareIds.contains(requestSoftwareId)) {
        Software newSoftware = softwareRepository.findByIdAndValidToIsNull(requestSoftwareId)
                .orElseThrow(() -> new NotFoundException("oprogramowanie", "id", requestSoftwareId));

        ComputerSetSoftware newComputerSetSoftware = new ComputerSetSoftware(computerSet, newSoftware);
        computerSetSoftwareRepository.save(newComputerSetSoftware);
      }
    });
    //STARE POŁĄCZENIE
    currentSoftwareIds.forEach(currentSoftwareId -> {
      if (!requestSoftwareIds.contains(currentSoftwareId)) {
        ComputerSetSoftware oldComputerSetSoftware = computerSetSoftwareRepository
                .findByComputerSetIdAndSoftwareIdAndValidToIsNull(computerSet.getId(), currentSoftwareId);
        oldComputerSetSoftware.setValidTo(LocalDateTime.now());
      }
    });
  }

  public void deleteComputerSet(Long id) throws NotFoundException {
    ComputerSet computerSet = computerSetRepository.findByIdAndValidToIsNull(id)
            .orElseThrow(() -> new NotFoundException("zestaw komputerowy", "id", id));
    computerSet.setValidTo(LocalDateTime.now());
    computerSetRepository.save(computerSet);

    affiliationComputerSetRepository.findAllByComputerSetIdAndValidToIsNull(computerSet.getId()).forEach(relation -> {
      relation.setValidTo(LocalDateTime.now());
      affiliationComputerSetRepository.save(relation);
    });

    computerSetHardwareRepository.findAllByComputerSetIdAndValidToIsNull(computerSet.getId()).forEach(relation -> {
      relation.setValidTo(LocalDateTime.now());
      computerSetHardwareRepository.save(relation);
    });

    computerSetSoftwareRepository.findAllByComputerSetIdAndValidToIsNull(computerSet.getId()).forEach(relation -> {
      relation.setValidTo(LocalDateTime.now());
      computerSetSoftwareRepository.save(relation);
    });
  }

  public ComputerSetDTO getOneComputerSet(Long id) {
    ComputerSetDTO dto = new ComputerSetDTO();
    Set<Long> hardwareIds = new HashSet<>();
    Set<Long> softwareIds = new HashSet<>();


    ComputerSet computerSet = computerSetRepository.findByIdAndValidToIsNull(id)
            .orElseThrow(() -> new NotFoundException("zestaw", "id", id));

    dto.setName(computerSet.getName());

    dto.setInventoryNumber(computerSet.getInventoryNumber());

    AffiliationComputerSet ac = affiliationComputerSetRepository.findByComputerSetIdAndValidToIsNull(id);

    dto.setAffiliationId(ac.getAffiliation().getId());

    Set<ComputerSetHardware> chs = computerSetHardwareRepository.findAllByComputerSetIdAndValidToIsNull(id);

    if (chs != null) {
      chs.forEach(ch -> {
        hardwareIds.add(ch.getHardware().getId());
      });
    }

    dto.setHardwareIds(hardwareIds);

    Set<ComputerSetSoftware> css = computerSetSoftwareRepository.findAllByComputerSetIdAndValidToIsNull(id);

    if (css != null) {
      css.forEach(cs -> {
        softwareIds.add(cs.getSoftware().getId());
      });
    }

    dto.setSoftwareIds(softwareIds);

    return dto;
  }

  public PaginatedResult<HistoryDTO> getComputerSetSoftwareHistory(Long computerSetId) {

    List<HistoryDTO> dtos = new ArrayList<>();

    ComputerSet cs = computerSetRepository.findById(computerSetId)
            .orElseThrow(() -> new NotFoundException("zestaw komputerowy", "id", computerSetId));

    Set<ComputerSetSoftware> computerSetSoftwareSet = cs.getComputerSetSoftwareSet();
    computerSetSoftwareSet.forEach(computerSetSoftware -> {
      HistoryDTO dto = new HistoryDTO();
      dto.setInventoryNumber(computerSetSoftware.getSoftware().getInventoryNumber());
      dto.setName(computerSetSoftware.getSoftware().getName());
      dto.setValidFrom(computerSetSoftware.getValidFrom());
      dto.setValidTo(computerSetSoftware.getValidTo());
      dtos.add(dto);
    });

    PaginatedResult<HistoryDTO> response = new PaginatedResult<>();
    response.setItems(dtos);
    response.setTotalElements((long) dtos.size());
    return response;
  }

  public PaginatedResult<HistoryDTO> getComputerSetHardwareHistory(Long computerSetId) {

    List<HistoryDTO> dtos = new ArrayList<>();
    ComputerSet cs = computerSetRepository.findById(computerSetId)
            .orElseThrow(() -> new NotFoundException("zestaw komputerowy", "id", computerSetId));
    Set<ComputerSetHardware> computerSetHardwareSet = cs.getComputerSetHardwareSet();
    computerSetHardwareSet.forEach(computerSetHardware -> {
      HistoryDTO dto = new HistoryDTO();
      dto.setInventoryNumber(computerSetHardware.getHardware().getInventoryNumber());
      dto.setName(computerSetHardware.getHardware().getName());
      dto.setValidFrom(computerSetHardware.getValidFrom());
      dto.setValidTo(computerSetHardware.getValidTo());
      dtos.add(dto);
    });

    PaginatedResult<HistoryDTO> response = new PaginatedResult<>();
    response.setItems(dtos);
    response.setTotalElements((long) dtos.size());
    return response;
  }

  public PaginatedResult<HistoryDTO> getComputerSetAffiliationsHistory(Long computerSetId) {
    List<HistoryDTO> dtos = new ArrayList<>();

    ComputerSet cs = computerSetRepository.findById(computerSetId)
            .orElseThrow(() -> new NotFoundException("zestaw komputerowy", "id", computerSetId));
    Set<AffiliationComputerSet> affiliationComputerSetSet = cs.getAffiliationComputerSetSet();
    affiliationComputerSetSet.forEach(affiliationComputerSet -> {
      HistoryDTO dto = new HistoryDTO();
      dto.setName(AffiliationService.generateName(affiliationComputerSet.getAffiliation()));
      dto.setValidFrom(affiliationComputerSet.getValidFrom());
      dto.setValidTo(affiliationComputerSet.getValidTo());
      dtos.add(dto);
    });

    PaginatedResult<HistoryDTO> response = new PaginatedResult<>();
    response.setItems(dtos);
    response.setTotalElements((long) dtos.size());
    return response;
  }

  //region metodyPomocnicze
  public Long getCurrentAffiliationId(ComputerSet cs) {

    for (AffiliationComputerSet affiliationComputerSet : cs.getAffiliationComputerSetSet()) {
      if (affiliationComputerSet.getValidTo() == null) {
        return (affiliationComputerSet.getAffiliation()).getId();
      }
    }
    return null;
  }

  public Set<Long> getCurrentHardwareIds(ComputerSet cs) {
    Set<Long> ids = new HashSet<>();

    cs.getComputerSetHardwareSet().forEach(computerSetHardware -> {
      if (computerSetHardware.getValidTo() == null) {
        ids.add(computerSetHardware.getHardware().getId());
      }
    });

    return ids;
  }

  public Set<Long> getCurrentSoftwareIds(ComputerSet cs) {
    Set<Long> ids = new HashSet<>();

    cs.getComputerSetSoftwareSet().forEach(computerSetSoftware -> {
      if (computerSetSoftware.getValidTo() == null) {
        ids.add(computerSetSoftware.getSoftware().getId());
      }
    });

    return ids;
  }

  public String getValidAffiliationName(ComputerSet cs) {
    for (AffiliationComputerSet affiliationComputerSet : cs.getAffiliationComputerSetSet()) {
      if (affiliationComputerSet.getValidTo() == null) {
        return AffiliationService.generateName(affiliationComputerSet.getAffiliation());
      }
    }
    return null;
  }

  public Set<String> getValidHardwareInventoryNumbers(ComputerSet cs) {
    Set<String> inventoryNumbers = new HashSet<>();

    cs.getComputerSetHardwareSet().forEach(computerSetHardware -> {
      if (computerSetHardware.getValidTo() == null) {
        inventoryNumbers.add(computerSetHardware.getHardware().getInventoryNumber());
      }
    });

    return inventoryNumbers;
  }

  public Set<String> getValidSoftwareInventoryNumbers(ComputerSet cs) {
    Set<String> inventoryNumbers = new HashSet<>();

    cs.getComputerSetSoftwareSet().forEach(computerSetSoftware -> {
      if (computerSetSoftware.getValidTo() == null) {
        inventoryNumbers.add(computerSetSoftware.getSoftware().getInventoryNumber());
      }
    });

    return inventoryNumbers;
  }
  //endregion
}
