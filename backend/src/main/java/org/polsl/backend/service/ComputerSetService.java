package org.polsl.backend.service;

import org.polsl.backend.dto.PaginatedResult;
import org.polsl.backend.dto.computerset.ComputerSetInputDTO;
import org.polsl.backend.dto.computerset.ComputerSetOutputDTO;
import org.polsl.backend.entity.*;
import org.polsl.backend.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
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

    Optional<Affiliation> affiliationOptional
            = affiliationRepository.findByIdAndFirstNameAndLastNameAndIsDeletedAndLocation(request.getAffiliation().getId(),
            request.getAffiliation().getFirstName(), request.getAffiliation().getLastName(), request.getAffiliation().getDeleted(),
            request.getAffiliation().getLocation());
    if (affiliationOptional.isPresent()) {
      AffiliationComputerSet affiliationComputerSet
              = new AffiliationComputerSet(request.getAffiliation(), computerSet, LocalDateTime.now());
      affiliationComputerSetRepository.save(affiliationComputerSet);
    } else {
      throw new EntityNotFoundException("W bazie danych nie ma takiej przynależności!");
    }

    request.getHardwareSet().forEach(hardware -> {
      Optional<Hardware> hardwareOptional
              = hardwareRepository.findByIdAndNameAndHardwareDictionary(hardware.getId(), hardware.getName(),
              hardware.getHardwareDictionary());
      if (hardwareOptional.isPresent()) {
        ComputerSetHardware computerSetHardware
                = new ComputerSetHardware(computerSet, hardware, LocalDateTime.now());
        computerSetHardwareRepository.save(computerSetHardware);
      } else {
        throw new EntityNotFoundException("W bazie danych nie ma takiego sprzętu!");
      }
    });

    request.getSoftwareSet().forEach(software -> {
      Optional<Software> softwareOptional
              = softwareRepository.findByIdAndName(software.getId(), software.getName());
      if (softwareOptional.isPresent()) {
        ComputerSetSoftware computerSetSoftware =
                new ComputerSetSoftware(computerSet, software, LocalDateTime.now());
        computerSetSoftwareRepository.save(computerSetSoftware);
      } else {
        throw new EntityNotFoundException("W bazie danych nie ma takiego oprogramowania!");
      }
    });

  }

}
