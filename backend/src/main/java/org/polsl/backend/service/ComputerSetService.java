package org.polsl.backend.service;

import org.polsl.backend.dto.PaginatedResult;
import org.polsl.backend.dto.computerset.ComputerSetDTO;
import org.polsl.backend.dto.computerset.ComputerSetOutputDTO;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
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

  public PaginatedResult<ComputerSetOutputDTO> getAllComputerSets() {
    Iterable<ComputerSet> computerSets = computerSetRepository.findAll();
    List<ComputerSetOutputDTO> dtos = new ArrayList<>();
    for (ComputerSet computerSet : computerSets) {
      ComputerSetOutputDTO dto = new ComputerSetOutputDTO();
      dto.setId(computerSet.getId());
      dto.setName(computerSet.getName());
      dtos.add(dto);
    }
    PaginatedResult<ComputerSetOutputDTO> response = new PaginatedResult<>();
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

    Long currentAffiliationId = computerSet.getCurrentAffiliationId();
    Set<Long> currentHardwareIds = computerSet.getCurrentHardwareIds();
    Set<Long> currentSoftwareIds = computerSet.getCurrentSoftwareIds();

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


}
