package org.polsl.backend;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.polsl.backend.dto.hardware.HardwareInputDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
@Transactional
@SqlGroup({
    @Sql(scripts = "/scripts/create-test-hardware_dictionary.sql"),
    @Sql(scripts = "/scripts/create-test-hardware.sql"),
    @Sql(scripts = "/scripts/create-test-computer_sets.sql"),
    @Sql(scripts = "/scripts/create-test-computer_sets_hardware.sql"),
    @Sql(scripts = "/scripts/create-test-affiliation.sql")
})
public class HardwareControllerIntegrationTest {
  @Autowired
  private MockMvc mvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Test
  public void givenCorrectRequest_whenGettingSoloHardwareList_thenReturnStatus200AndData() throws Exception {
    mvc.perform(get("/api/hardware"))
        .andExpect(status().is(200))
        .andExpect(jsonPath("$.totalElements").value(3))
        .andExpect(jsonPath("$.items", hasSize(3)))
        .andExpect(jsonPath("$.items[0].id").value(2))
        .andExpect(jsonPath("$.items[0].name").value("TP-Link"))
        .andExpect(jsonPath("$.items[0].type").value("Karta sieciowa"))
        .andExpect(jsonPath("$.items[1].id").value(4))
        .andExpect(jsonPath("$.items[1].name").value("i3-5400"))
        .andExpect(jsonPath("$.items[1].type").value("Procesor"))
        .andExpect(jsonPath("$.items[2].id").value(5))
        .andExpect(jsonPath("$.items[2].name").value("HD 7770"))
        .andExpect(jsonPath("$.items[2].type").value("Karta graficzna"));
  }

  @Test
  public void givenEmptyRequest_whenAddingHardware_thenReturnStatus400() throws Exception {
    HardwareInputDTO request = new HardwareInputDTO();
    mvc.perform(post("/api/hardware")
        .content(objectMapper.writeValueAsString(request))
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().is(400))
        .andExpect(jsonPath("$.fieldErrors", hasSize(3)))
        .andExpect(jsonPath("$.fieldErrors[?(@.field =~ /name/)].message").value("must not be empty"))
        .andExpect(jsonPath("$.fieldErrors[?(@.field =~ /dictionaryId/)].message").value("must not be null"))
        .andExpect(jsonPath("$.fieldErrors[?(@.field =~ /affiliationId/)].message").value("must not be null"));
  }

  @Test
  public void givenCorrectRequestWithComputerSetId_whenAddingHardware_thenReturnStatus200AndData() throws Exception {
    HardwareInputDTO request = new HardwareInputDTO();
    request.setName("GTX 7070");
    request.setAffiliationId((long) 1);
    request.setComputerSetId((long) 1);
    request.setDictionaryId((long) 1);
    mvc.perform(post("/api/hardware")
        .content(objectMapper.writeValueAsString(request))
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().is(200))
        .andExpect(jsonPath("$.success").value(true))
        .andExpect(jsonPath("$.message").value("Utworzono sprzęt"));
  }

  @Test
  public void givenCorrectRequestWithoutComputerSetId_whenAddingHardware_thenReturnStatus200AndData() throws Exception {
    HardwareInputDTO request = new HardwareInputDTO();
    request.setName("GTX 7050");
    request.setAffiliationId((long) 2);
    request.setDictionaryId((long) 1);
    mvc.perform(post("/api/hardware")
        .content(objectMapper.writeValueAsString(request))
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().is(200))
        .andExpect(jsonPath("$.success").value(true))
        .andExpect(jsonPath("$.message").value("Utworzono sprzęt"));
  }
}
