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

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

  public void createSoftware(SoftwareInputDTO request) {
    Software newSoftware = new Software();
    newSoftware.setName(request.getName());
    Software softwareWithNewId = softwareRepository.save(newSoftware);

    for (Long id : request.getComputerSetIds()) {
      ComputerSetSoftwareKey key = new ComputerSetSoftwareKey();
      key.setSoftwareId(softwareWithNewId.getId());
      key.setComputerSetId(computerSetRepository.findById(id)
          .orElseThrow(() -> new NotFoundException("zestaw komputerowy", "id", id)).getId());
      key.setValidFrom(LocalDateTime.now());

      ComputerSetSoftware computerSetSoftware = new ComputerSetSoftware();
      computerSetSoftware.setId(key);
      computerSetSoftwareRepository.save(computerSetSoftware);
    }
  }

  public void editSoftware(Long id, SoftwareInputDTO request) throws NotFoundException {
    Software software = softwareRepository.findById(id).orElseThrow(() -> new NotFoundException("Oprogramowanie", "id", id));
    software.setName(request.getName());
    softwareRepository.save(software);
  }

  public void deleteSoftware(Long id) {
    softwareRepository.findAllById(id).orElseThrow(() -> new NotFoundException("Oprogramowanie", "id", id));
    softwareRepository.deleteById(id);
  }
}
