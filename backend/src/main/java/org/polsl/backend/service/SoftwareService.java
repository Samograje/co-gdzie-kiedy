package org.polsl.backend.service;

import org.polsl.backend.dto.PaginatedResult;
import org.polsl.backend.dto.devices.DeviceOutputDTO;
import org.polsl.backend.dto.software.SoftwareDTO;
import org.polsl.backend.dto.software.SoftwareOutputDTO;
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

  public PaginatedResult<SoftwareDTO> getAllSoftware()
  {
    Iterable<Software> softwares = softwareRepository.findAllBySoftwareIsNull();
    List<SoftwareDTO> softwareDTO = new ArrayList();
    for(Software software : softwares){
      SoftwareDTO dto = new SoftwareDTO();
      dto.setName(software.getName());
    }

    PaginatedResult<SoftwareDTO> response = new PaginatedResult<>();
    response.setItems(softwareDTO);
    response.setTotalElements((long)softwareDTO.size());
    return response;
  }
}
