package org.polsl.backend.service;

import org.polsl.backend.dto.PaginatedResult;
import org.polsl.backend.dto.computerset.ComputerSetInputDTO;
import org.polsl.backend.dto.computerset.ComputerSetOutputDTO;
import org.polsl.backend.entity.*;
import org.polsl.backend.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

  public void createComputerSet(ComputerSetInputDTO request) {
    ComputerSet computerSet = new ComputerSet();
    computerSet.setName(request.getName());
    computerSetRepository.save(computerSet);

    Optional<Affiliation> affiliationOptional = affiliationRepository.findById(request.getAffiliationId());
    if (affiliationOptional.isPresent()) {
      AffiliationComputerSet affiliationComputerSet
              = new AffiliationComputerSet(affiliationOptional.get(), computerSet, LocalDateTime.now());
      affiliationComputerSetRepository.save(affiliationComputerSet);
    }

    request.getHardwareIds().forEach(hardwareId -> {
      Optional<Hardware> hardwareOptional = hardwareRepository.findById(hardwareId);
      if (hardwareOptional.isPresent()) {
        ComputerSetHardware computerSetHardware =
                new ComputerSetHardware(computerSet, hardwareOptional.get(), LocalDateTime.now());
        computerSetHardwareRepository.save(computerSetHardware);
      }
    });

    request.getSoftwareIds().forEach(softwareId -> {
      Optional<Software> softwareOptional = softwareRepository.findById(softwareId);
      if (softwareOptional.isPresent()) {
        ComputerSetSoftware computerSetSoftware =
                new ComputerSetSoftware(computerSet, softwareOptional.get(), LocalDateTime.now());
        computerSetSoftwareRepository.save(computerSetSoftware);
      }
    });

  }

}
