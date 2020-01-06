package org.polsl.backend.service;

import org.polsl.backend.dto.hardwaredictionary.HardwareDictionaryListOutputDTO;
import org.polsl.backend.entity.HardwareDictionary;
import org.polsl.backend.repository.HardwareDictionaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class HardwareDictionaryService {
  private HardwareDictionaryRepository hardwareDictionaryRepository;

  @Autowired
  public HardwareDictionaryService(HardwareDictionaryRepository hardwareDictionaryRepository) {
    this.hardwareDictionaryRepository = hardwareDictionaryRepository;
  }

  public List<HardwareDictionaryListOutputDTO> getAllHardwareDictionaries() {
    Iterable<HardwareDictionary> hardwareDictionaries = hardwareDictionaryRepository.findAll();
    List<HardwareDictionaryListOutputDTO> dtos = new ArrayList<>();
    for(HardwareDictionary hardwareDictionary : hardwareDictionaries){
      HardwareDictionaryListOutputDTO dto = new HardwareDictionaryListOutputDTO();
      dto.setId(hardwareDictionary.getId());
      dto.setValue(hardwareDictionary.getValue());
      dtos.add(dto);
    }
    return  dtos;
  }
}
