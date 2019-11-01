package org.polsl.backend.service;

import org.polsl.backend.dto.PaginatedResult;
import org.polsl.backend.dto.computerset.ComputerSetInputDTO;
import org.polsl.backend.dto.computerset.ComputerSetOutputDTO;
import org.polsl.backend.entity.AffiliationComputerSet;
import org.polsl.backend.entity.ComputerSet;
import org.polsl.backend.repository.AffiliationComputerSetRepository;
import org.polsl.backend.repository.ComputerSetHardwareRepository;
import org.polsl.backend.repository.ComputerSetRepository;
import org.polsl.backend.repository.ComputerSetSoftwareRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

  @Autowired
  public ComputerSetService(ComputerSetRepository computerSetRepository,
                            AffiliationComputerSetRepository affiliationComputerSetRepository,
                            ComputerSetHardwareRepository computerSetHardwareRepository,
                            ComputerSetSoftwareRepository computerSetSoftwareRepository) {
    this.computerSetRepository = computerSetRepository;
    this.affiliationComputerSetRepository = affiliationComputerSetRepository;
    this.computerSetHardwareRepository = computerSetHardwareRepository;
    this.computerSetSoftwareRepository = computerSetSoftwareRepository;
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

    AffiliationComputerSet affiliationComputerSet
            = new AffiliationComputerSet(request.getAffiliation(), computerSet, LocalDateTime.now());
    affiliationComputerSetRepository.save(affiliationComputerSet);

    /*request.getHardwareSet().forEach(hardware -> {
      ComputerSetHardware computerSetHardware =
              new ComputerSetHardware(computerSet,hardware,LocalDateTime.now());
      computerSetHardwareRepository.save(computerSetHardware);
    });

    request.getSoftwareSet().forEach(software -> {
      ComputerSetSoftware computerSetSoftware =
              new ComputerSetSoftware(computerSet,software,LocalDateTime.now());
      computerSetSoftwareRepository.save(computerSetSoftware);
    });*/

  }

}
