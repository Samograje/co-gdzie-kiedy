package org.polsl.backend.service;

import org.polsl.backend.dto.PaginatedResult;
import org.polsl.backend.dto.software.SoftwareInputDTO;
import org.polsl.backend.dto.software.SoftwareOutputDTO;
import org.polsl.backend.entity.ComputerSetSoftware;
import org.polsl.backend.entity.Software;
import org.polsl.backend.exception.NotFoundException;
import org.polsl.backend.key.ComputerSetSoftwareKey;
import org.polsl.backend.repository.ComputerSetRepository;
import org.polsl.backend.repository.ComputerSetSoftwareRepository;
import org.polsl.backend.repository.SoftwareRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Logika biznesowa oprogramowania.
 */
@Service
public class SoftwareService {
  private SoftwareRepository softwareRepository;
  private ComputerSetRepository computerSetRepository;
  private ComputerSetSoftwareRepository computerSetSoftwareRepository;

  @Autowired
  public SoftwareService(SoftwareRepository softwareRepository,
                         ComputerSetRepository computerSetRepository,
                         ComputerSetSoftwareRepository computerSetSoftwareRepository) {
    this.softwareRepository = softwareRepository;
    this.computerSetRepository = computerSetRepository;
    this.computerSetSoftwareRepository = computerSetSoftwareRepository;
  }

  public PaginatedResult<SoftwareOutputDTO> getAllSoftware() {
    Iterable<Software> softwares = softwareRepository.findAll();
    List<SoftwareOutputDTO> softwareOutputDTO = new ArrayList<>();
    for (Software software : softwares) {
      SoftwareOutputDTO dto = new SoftwareOutputDTO();
      dto.setName(software.getName());
      softwareOutputDTO.add(dto);
    }

    PaginatedResult<SoftwareOutputDTO> response = new PaginatedResult<>();
    response.setItems(softwareOutputDTO);
    response.setTotalElements((long) softwareOutputDTO.size());
    return response;
  }

  @Transactional
  public void createSoftware(SoftwareInputDTO request) {
    Software newSoftware = new Software();
    newSoftware.setName(request.getName());
    softwareRepository.save(newSoftware);
    Set<Long> computerSetIdsSet = request.getComputerSetIds();
    if(computerSetIdsSet != null)
    {
      for (Long id : computerSetIdsSet) {
        ComputerSetSoftwareKey key = new ComputerSetSoftwareKey();
        key.setSoftwareId(newSoftware.getId());
        key.setComputerSetId(computerSetRepository.findById(id)
        .orElseThrow(() -> new NotFoundException("Zestaw komputerowy", "id", id)).getId());
        key.setValidFrom(LocalDateTime.now());
        ComputerSetSoftware computerSetSoftware = new ComputerSetSoftware();
        computerSetSoftware.setId(key);
        computerSetSoftware.setValidTo(null);
        computerSetSoftwareRepository.save(computerSetSoftware);
      }
    }
  }

  public void editSoftware(Long id, SoftwareInputDTO request) throws NotFoundException {
    Software software = softwareRepository.findById(id).orElseThrow(() -> new NotFoundException("Oprogramowanie", "id", id));
    software.setName(request.getName());
    softwareRepository.save(software);
    Set<ComputerSetSoftware> computerSetSoftwareSet = computerSetSoftwareRepository.findAllBySoftwareId(id);
    System.out.println(computerSetSoftwareSet.size());
    for(ComputerSetSoftware ID : computerSetSoftwareSet)
    {
      //Dodaj nowy rekord do historii z tym samym id oprogramowania i z nowym id komputera. Skąd id komputera??
      System.out.println("SOFT ID:" + ID.getSoftware().getId());
      System.out.println("COMPUTER SET ID ID:" + ID.getComputerSet().getId());
    }

  }

  public void deleteSoftware(Long id) {
    softwareRepository.findAllById(id).orElseThrow(() -> new NotFoundException("Oprogramowanie", "id", id));
    softwareRepository.deleteById(id);
  }
}
