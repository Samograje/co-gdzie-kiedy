package org.polsl.backend;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.polsl.backend.dto.affiliation.AffiliationDTO;
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

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
@Transactional
@SqlGroup({
  @Sql(scripts = "/scripts/create-test-affiliation.sql"),
  @Sql(scripts = "/scripts/create-test-hardware_dictionary.sql"),
  @Sql(scripts = "/scripts/create-test-hardware.sql"),
  @Sql(scripts = "/scripts/create-test-computer_sets.sql"),
  @Sql(scripts = "/scripts/create-test-affiliation_hardware.sql"),
  @Sql(scripts = "/scripts/create-test-affiliations_computer_sets.sql"),
})
public class AffiliationControllerIntegrationTest {

  @Autowired
  private MockMvc mvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Test
  public void givenCorrectRequest_whenGettingAffiliationsList_thenReturnStatus200AndData() throws Exception {
    mvc.perform(get("/api/affiliations"))
      .andExpect(status().is(200))
      .andExpect(jsonPath("$.totalElements").value(3))
      .andExpect(jsonPath("$.items", hasSize(3)))
      .andExpect(jsonPath("$.items[0].id").value(1))
      .andExpect(jsonPath("$.items[0].firstName").value("Szymon"))
      .andExpect(jsonPath("$.items[0].lastName").value("Jęczyzel"))
      .andExpect(jsonPath("$.items[0].location").value("Solaris"))
      .andExpect(jsonPath("$.items[0].computerSetsInventoryNumbers", hasSize(1)))
      .andExpect(jsonPath("$.items[0].computerSetsInventoryNumbers", containsInAnyOrder("C1/2019")))
      .andExpect(jsonPath("$.items[0].hardwareInventoryNumbers", hasSize(1)))
      .andExpect(jsonPath("$.items[0].hardwareInventoryNumbers", containsInAnyOrder("H1/2019")))
      .andExpect(jsonPath("$.items[1].id").value(2))
      .andExpect(jsonPath("$.items[1].firstName").value("Bartłomiej"))
      .andExpect(jsonPath("$.items[1].lastName").value("Szlachta"))
      .andExpect(jsonPath("$.items[1].location").value("130"))
      .andExpect(jsonPath("$.items[1].computerSetsInventoryNumbers", hasSize(2)))
      .andExpect(jsonPath("$.items[1].computerSetsInventoryNumbers", containsInAnyOrder("C3/2019", "C2/2019")))
      .andExpect(jsonPath("$.items[1].hardwareInventoryNumbers", hasSize(2)))
      .andExpect(jsonPath("$.items[1].hardwareInventoryNumbers", containsInAnyOrder("H3/2019", "H2/2019")))
      .andExpect(jsonPath("$.items[2].id").value(4))
      .andExpect(jsonPath("$.items[2].firstName").value(""))
      .andExpect(jsonPath("$.items[2].lastName").value(""))
      .andExpect(jsonPath("$.items[2].location").value("Sala 510"))
      .andExpect(jsonPath("$.items[2].computerSetsInventoryNumbers", hasSize(0)))
      .andExpect(jsonPath("$.items[2].hardwareInventoryNumbers", hasSize(1)))
      .andExpect(jsonPath("$.items[2].hardwareInventoryNumbers", containsInAnyOrder("H5/2019")));
  }

  @Test
  public void givenInvalidId_whenGettingAffiliation_thenReturnStatus400() throws Exception {
    mvc.perform(get("/api/affiliations/abcd"))
      .andExpect(status().is(400))
      .andExpect(jsonPath("$.success").value(false))
      .andExpect(jsonPath("$.message").value("Podana wartość nie jest liczbą"));
  }

  @Test
  public void givenNotExistingId_whenGettingAffiliation_thenReturnStatus404() throws Exception {
    mvc.perform(get("/api/affiliations/0"))
      .andExpect(status().is(404))
      .andExpect(jsonPath("$.success").value(false))
      .andExpect(jsonPath("$.message").value("Nie istnieje przynależność o id: '0'"));
  }

  @Test
  public void givenCorrectRequest_whenGettingAffiliation_thenReturnStatus200AndData() throws Exception {
    mvc.perform(get("/api/affiliations/1"))
      .andExpect(status().is(200))
      .andExpect(jsonPath("$.firstName").value("Szymon"))
      .andExpect(jsonPath("$.lastName").value("Jęczyzel"))
      .andExpect(jsonPath("$.location").value("Solaris"));
  }

  @Test
  public void givenEmptyRequest_whenAddingAffiliation_thenReturnStatus400() throws Exception {
    AffiliationDTO request = new AffiliationDTO();
    mvc.perform(post("/api/affiliations")
      .content(objectMapper.writeValueAsString(request))
      .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().is(400))
      .andExpect(jsonPath("$.fieldErrors", hasSize(3)))
      .andExpect(jsonPath("$.fieldErrors[?(@.field =~ /firstName/)].message").value("must not be null"))
      .andExpect(jsonPath("$.fieldErrors[?(@.field =~ /lastName/)].message").value("must not be null"))
      .andExpect(jsonPath("$.fieldErrors[?(@.field =~ /location/)].message").value("must not be null"));
  }

  @Test
  public void givenCorrectRequest_whenAddingAffiliation_thenReturnStatus200AndData() throws Exception {
    AffiliationDTO request = new AffiliationDTO();
    request.setFirstName("Mike");
    request.setLastName("Ehrmantraut");
    request.setLocation("");
    mvc.perform(post("/api/affiliations")
      .content(objectMapper.writeValueAsString(request))
      .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().is(200))
      .andExpect(jsonPath("$.success").value(true))
      .andExpect(jsonPath("$.message").value("Utworzono przynależność"));
  }

  @Test
  public void givenEmptyRequest_whenEditingAffiliation_thenReturnStatus400() throws Exception {
    AffiliationDTO request = new AffiliationDTO();
    mvc.perform(put("/api/affiliations/1")
      .content(objectMapper.writeValueAsString(request))
      .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().is(400))
      .andExpect(jsonPath("$.fieldErrors", hasSize(3)))
      .andExpect(jsonPath("$.fieldErrors[?(@.field =~ /firstName/)].message").value("must not be null"))
      .andExpect(jsonPath("$.fieldErrors[?(@.field =~ /lastName/)].message").value("must not be null"))
      .andExpect(jsonPath("$.fieldErrors[?(@.field =~ /location/)].message").value("must not be null"));
  }

  @Test
  public void givenInvalidId_whenEditingAffiliation_thenReturnStatus404() throws Exception {
    AffiliationDTO request = new AffiliationDTO();
    request.setFirstName("Mike");
    request.setLastName("Ehrmantraut");
    request.setLocation("");
    mvc.perform(put("/api/affiliations/0")
      .content(objectMapper.writeValueAsString(request))
      .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().is(404))
      .andExpect(jsonPath("$.success").value(false))
      .andExpect(jsonPath("$.message").value("Nie istnieje przynależność o id: '0'"));
  }

  @Test
  public void givenNoId_whenEditingAffiliation_thenReturnStatus405() throws Exception {
    mvc.perform(put("/api/affiliations")
      .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().is(405));
  }

  @Test
  public void givenCorrectRequest_whenEditingAffiliation_thenReturnStatus200AndData() throws Exception {
    AffiliationDTO request = new AffiliationDTO();
    request.setFirstName("Mike");
    request.setLastName("Ehrmantraut");
    request.setLocation("");
    mvc.perform(put("/api/affiliations/1")
      .content(objectMapper.writeValueAsString(request))
      .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().is(200))
      .andExpect(jsonPath("$.success").value(true))
      .andExpect(jsonPath("$.message").value("Zaktualizowano parametry przynależności"));
  }

  @Test
  public void givenInvalidId_whenDeletingAffiliation_thenReturnStatus404() throws Exception {
    mvc.perform(delete("/api/affiliations/0"))
      .andExpect(status().is(404))
      .andExpect(jsonPath("$.success").value(false))
      .andExpect(jsonPath("$.message").value("Nie istnieje przynależność o id: '0'"));
  }

  @Test
  public void givenNoId_whenDeletingAffiliation_thenReturnStatus405() throws Exception {
    mvc.perform(delete("/api/affiliations"))
      .andExpect(status().is(405));
  }

  @Test
  public void givenCorrectRequest_whenDeletingAffiliation_thenReturnStatus200AndData() throws Exception {
    mvc.perform(delete("/api/affiliations/1"))
      .andExpect(status().is(200))
      .andExpect(jsonPath("$.success").value(true))
      .andExpect(jsonPath("$.message").value("Usunięto przynależność"));
  }
}
