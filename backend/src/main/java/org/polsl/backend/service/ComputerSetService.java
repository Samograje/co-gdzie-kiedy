package org.polsl.backend.service;

import org.polsl.backend.dto.PaginatedResult;
import org.polsl.backend.dto.computerset.ComputerSetInputDTO;
import org.polsl.backend.dto.computerset.ComputerSetOutputDTO;
import org.polsl.backend.entity.Affiliation;
import org.polsl.backend.entity.AffiliationComputerSet;
import org.polsl.backend.entity.ComputerSet;
import org.polsl.backend.entity.ComputerSetHardware;
import org.polsl.backend.entity.ComputerSetSoftware;
import org.polsl.backend.entity.Hardware;
import org.polsl.backend.entity.Software;
import org.polsl.backend.exception.NotFoundException;
import org.polsl.backend.repository.AffiliationComputerSetRepository;
import org.polsl.backend.repository.AffiliationRepository;
import org.polsl.backend.repository.ComputerSetHardwareRepository;
import org.polsl.backend.repository.ComputerSetRepository;
import org.polsl.backend.repository.ComputerSetSoftwareRepository;
import org.polsl.backend.repository.HardwareRepository;
import org.polsl.backend.repository.SoftwareRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
  public void createComputerSet(ComputerSetInputDTO request) {
    ComputerSet computerSet = new ComputerSet();
    computerSet.setName(request.getName());
    computerSetRepository.save(computerSet);

    Affiliation affiliation = affiliationRepository.findById(request.getAffiliationId())
        .orElseThrow(() -> new NotFoundException("przynależność", "id", request.getAffiliationId()));
    AffiliationComputerSet affiliationComputerSet = new AffiliationComputerSet(affiliation, computerSet);
    affiliationComputerSetRepository.save(affiliationComputerSet);

    if (request.getHardwareIds() != null) {
      request.getHardwareIds().forEach(hardwareId -> {
        Hardware hardware = hardwareRepository.findById(hardwareId)
                .orElseThrow(() -> new NotFoundException("sprzęt", "id", hardwareId));
        ComputerSetHardware computerSetHardware = new ComputerSetHardware(computerSet, hardware);
        computerSetHardwareRepository.save(computerSetHardware);
      });
    }

    if (request.getSoftwareIds() != null) {
      request.getSoftwareIds().forEach(softwareId -> {
        Software software = softwareRepository.findById(softwareId)
                .orElseThrow(() -> new NotFoundException("oprogramowanie", "id", softwareId));
        ComputerSetSoftware computerSetSoftware = new ComputerSetSoftware(computerSet, software);
        computerSetSoftwareRepository.save(computerSetSoftware);
      });
    }
  }

  public void editComputerSet(Long id, ComputerSetInputDTO request) throws NotFoundException {
    ComputerSet computerSet = computerSetRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("zestaw komputerowy", "id", id));
    computerSet.setName(request.getName());
    computerSetRepository.save(computerSet);

    Affiliation affiliation = affiliationRepository.findById(request.getAffiliationId())
            .orElseThrow(() -> new NotFoundException("przynależność", "id", request.getAffiliationId()));
    AffiliationComputerSet affiliationComputerSet = new AffiliationComputerSet(affiliation, computerSet);
    affiliationComputerSetRepository.save(affiliationComputerSet);

    if (request.getHardwareIds() != null) {
      request.getHardwareIds().forEach(hardwareId -> {
        Hardware hardware = hardwareRepository.findById(hardwareId)
                .orElseThrow(() -> new NotFoundException("sprzęt", "id", hardwareId));
        ComputerSetHardware computerSetHardware = new ComputerSetHardware(computerSet, hardware);
        computerSetHardwareRepository.save(computerSetHardware);
      });
    }

    if (request.getSoftwareIds() != null) {
      request.getSoftwareIds().forEach(softwareId -> {
        Software software = softwareRepository.findById(softwareId)
                .orElseThrow(() -> new NotFoundException("oprogramowanie", "id", softwareId));
        ComputerSetSoftware computerSetSoftware = new ComputerSetSoftware(computerSet, software);
        computerSetSoftwareRepository.save(computerSetSoftware);
      });
    }
  }

  public void deleteComputerSet(Long id) throws NotFoundException {
    ComputerSet computerSet = computerSetRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("zestaw komputerowy", "id", id));
    computerSet.setValidTo(LocalDateTime.now());
    computerSetRepository.save(computerSet);
  }

}
