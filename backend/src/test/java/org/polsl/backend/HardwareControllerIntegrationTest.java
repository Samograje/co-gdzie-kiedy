package org.polsl.backend;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
    @Sql(scripts = "/scripts/create-test-computer_sets_hardware.sql")
})
public class HardwareControllerIntegrationTest {
  @Autowired
  private MockMvc mvc;

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
}
