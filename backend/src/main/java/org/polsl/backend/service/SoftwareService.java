package org.polsl.backend.service;

import org.polsl.backend.dto.PaginatedResult;
import org.polsl.backend.dto.software.SoftwareInputDTO;
import org.polsl.backend.dto.software.SoftwareOutputDTO;
import org.polsl.backend.entity.ComputerSetSoftware;
import org.polsl.backend.entity.Software;
import org.polsl.backend.exception.NotFoundException;
import org.polsl.backend.repository.ComputerSetRepository;
import org.polsl.backend.repository.ComputerSetSoftwareRepository;
import org.polsl.backend.repository.SoftwareRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

  @Autowired
  public SoftwareService(SoftwareRepository softwareRepository) {
    this.softwareRepository = softwareRepository;
  }

  @Autowired
  public ComputerSetRepository computerSetRepository;

  @Autowired
  public ComputerSetSoftwareRepository computerSetSoftwareRepository;

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

  public void createSoftware(SoftwareInputDTO request) {
    Software software = new Software();
    software.setName(request.getName());
    softwareRepository.save(software);

    Set<Long> computerSetIdsSet = request.getComputerSetIds();
    for(Long id : computerSetIdsSet)
    {
      System.out.println(computerSetRepository.findById(id).get().getId());
      ComputerSetSoftware computerSetSoftware = new ComputerSetSoftware();
      computerSetSoftware.setSoftware(software);
      computerSetSoftware.setComputerSet(computerSetRepository.findById(id).get());
      computerSetSoftware.setValidFrom(LocalDateTime.now());
      computerSetSoftware.setValidTo(LocalDateTime.now());
      //computerSetSoftwareRepository.save(computerSetSoftware);
    }
    }




  public void editSoftware(Long id, SoftwareInputDTO request) throws NotFoundException {
    Software software = softwareRepository.findAllById(id).orElseThrow(() -> new NotFoundException("Oprogramowanie", "id", id));
    software.setName(request.getName());
    softwareRepository.save(software);
  }

  public void deleteSoftware(Long id) {
    Software software = softwareRepository.findAllById(id).orElseThrow(() -> new NotFoundException("Oprogramowanie", "id", id));
    softwareRepository.deleteById(id);
  }
}
