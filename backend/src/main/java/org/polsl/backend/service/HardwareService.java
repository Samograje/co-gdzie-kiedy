package org.polsl.backend.service;

import org.polsl.backend.dto.PaginatedResult;
import org.polsl.backend.dto.hardware.HardwareOutputDTO;
import org.polsl.backend.entity.Hardware;
import org.polsl.backend.repository.HardwareRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class HardwareService {
  private HardwareRepository hardwareRepository;

  @Autowired
  public HardwareService(HardwareRepository hardwareRepository) {
    this.hardwareRepository = hardwareRepository;
  }

  public PaginatedResult<HardwareOutputDTO> getAllHardwareAndComputerSets(){
    Iterable<Hardware> hardwares = hardwareRepository.findAll();
    List<HardwareOutputDTO> dtos = new ArrayList<>();
    for(Hardware hardware : hardwares){
      HardwareOutputDTO dto = new HardwareOutputDTO();
      dto.setId(hardware.getId());
      dto.setName(hardware.getName());
      dtos.add(dto);
    }
    PaginatedResult<HardwareOutputDTO> response = new PaginatedResult<>();
    response.setItems(dtos);
    response.setTotalElements((long)dtos.size());
    return response;
  }
}
