package org.polsl.backend.service;

import org.polsl.backend.dto.PaginatedResult;
import org.polsl.backend.dto.computerset.ComputerSetOutputDTO;
import org.polsl.backend.entity.ComputerSet;
import org.polsl.backend.repository.ComputerSetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Logika biznesowa zestawów komputerowych.
 */
@Service
public class ComputerSetService {
  private final ComputerSetRepository computerSetRepository;

  @Autowired
  public ComputerSetService(ComputerSetRepository computerSetRepository) {
    this.computerSetRepository = computerSetRepository;
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
}
