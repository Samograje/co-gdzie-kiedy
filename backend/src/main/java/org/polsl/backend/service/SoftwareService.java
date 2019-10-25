package org.polsl.backend.service;

import org.polsl.backend.dto.PaginatedResult;
import org.polsl.backend.dto.software.SoftwareInputDTO;
import org.polsl.backend.entity.Software;
import org.polsl.backend.repository.SoftwareRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SoftwareService {
  private SoftwareRepository softwareRepository;

  @Autowired
  public SoftwareService(SoftwareRepository softwareRepository) {
    this.softwareRepository = softwareRepository;
  }

  public PaginatedResult<SoftwareInputDTO> getAllSoftware()
  {
    Iterable<Software> softwares = softwareRepository.findAll();
    List<SoftwareInputDTO> softwareInputDTO = new ArrayList();
    for(Software software : softwares){
      SoftwareInputDTO dto = new SoftwareInputDTO();
      dto.setName(software.getName());
    }

    PaginatedResult<SoftwareInputDTO> response = new PaginatedResult<>();
    response.setItems(softwareInputDTO);
    response.setTotalElements((long) softwareInputDTO.size());
    return response;
  }
}
