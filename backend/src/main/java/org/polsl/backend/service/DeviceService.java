package org.polsl.backend.service;

import org.polsl.backend.dto.PaginatedResult;
import org.polsl.backend.dto.devices.DeviceOutputDTO;
import org.polsl.backend.entity.ComputerSet;
import org.polsl.backend.entity.Hardware;
import org.polsl.backend.repository.ComputerSetRepository;
import org.polsl.backend.repository.HardwareRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Logika biznesowa hardware'u i zestawów komputerowych.
 */
@Service
public class DeviceService {
  private HardwareRepository hardwareRepository;
  private ComputerSetRepository computerSetRepository;

  @Autowired
  public DeviceService(HardwareRepository hardwareRepository, ComputerSetRepository computerSetRepository) {
    this.hardwareRepository = hardwareRepository;
    this.computerSetRepository = computerSetRepository;
  }

  public PaginatedResult<DeviceOutputDTO> getAllComputerSetsAndSoloHardware() {
    Iterable<Hardware> manyHardware = hardwareRepository.findAllSoloHardware();
    List<DeviceOutputDTO> dtos = new ArrayList<>();

    Iterable<ComputerSet> computerSets = computerSetRepository.findAll();
    for (ComputerSet computerSet : computerSets) {
      DeviceOutputDTO dto = new DeviceOutputDTO();
      dto.setId(computerSet.getId());
      dto.setName(computerSet.getName());
      dto.setType("Zestaw");
      dto.setComputerSet(true);
      dtos.add(dto);
    }

    for (Hardware hardware : manyHardware) {
      DeviceOutputDTO dto = new DeviceOutputDTO();
      dto.setId(hardware.getId());
      dto.setName(hardware.getName());
      dto.setType(hardware.getHardwareDictionary().getValue());
      dto.setComputerSet(false);
      dtos.add(dto);
    }

    PaginatedResult<DeviceOutputDTO> response = new PaginatedResult<>();
    response.setItems(dtos);
    response.setTotalElements((long) dtos.size());
    return response;
  }
}
