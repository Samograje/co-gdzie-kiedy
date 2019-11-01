package org.polsl.backend.service;

import org.polsl.backend.dto.PaginatedResult;
import org.polsl.backend.dto.computerset.ComputerSetInputDTO;
import org.polsl.backend.dto.computerset.ComputerSetOutputDTO;
import org.polsl.backend.entity.AffiliationComputerSet;
import org.polsl.backend.entity.ComputerSet;
import org.polsl.backend.key.AffiliationComputerSetKey;
import org.polsl.backend.repository.AffiliationComputerSetRepository;
import org.polsl.backend.repository.ComputerSetRepository;
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

  @Autowired
  public ComputerSetService(ComputerSetRepository computerSetRepository,
                            AffiliationComputerSetRepository affiliationComputerSetRepository) {
    this.computerSetRepository = computerSetRepository;
    this.affiliationComputerSetRepository = affiliationComputerSetRepository;
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
    AffiliationComputerSet affiliationComputerSet = new AffiliationComputerSet();
    AffiliationComputerSetKey affiliationComputerSetKey
            = new AffiliationComputerSetKey(request.getAffiliation().getId(),
            computerSet.getId(), LocalDateTime.now());
    affiliationComputerSet.setId(affiliationComputerSetKey);
    affiliationComputerSet.setAffiliation(request.getAffiliation());
    affiliationComputerSet.setComputerSet(computerSet);
    affiliationComputerSet.setValidFrom(LocalDateTime.now());
    affiliationComputerSetRepository.save(affiliationComputerSet);
  }

}
