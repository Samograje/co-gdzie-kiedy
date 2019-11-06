package org.polsl.backend;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.polsl.backend.dto.software.SoftwareInputDTO;
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

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
@Transactional
@SqlGroup({
        @Sql(scripts = "/scripts/create-test-software.sql")
})
public class SoftwareControllerIntegrationTest {
  @Autowired
  private MockMvc mvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Test
  public void givenCorrectRequest_whenGettingSoftwareList_thenReturnStatus200AndData() throws Exception {
    mvc.perform(get("/api/software"))
            .andExpect(status().is(200))
            .andExpect(jsonPath("$.totalElements").value(4))
            .andExpect(jsonPath("$.items", hasSize(4)))
            .andExpect(jsonPath("$.items[0].id").value(1))
            .andExpect(jsonPath("$.items[0].name").value("Photoshop"))
            .andExpect(jsonPath("$.items[1].id").value(2))
            .andExpect(jsonPath("$.items[1].name").value("Visual Studio"))
            .andExpect(jsonPath("$.items[2].id").value(3))
            .andExpect(jsonPath("$.items[2].name").value("Postman"))
            .andExpect(jsonPath("$.items[3].id").value(4))
            .andExpect(jsonPath("$.items[3].name").value("Mathematica"));
  }

  @Test
  public void givenEmptyRequest_whenAddingAffiliation_thenReturnStatus400() throws Exception {
    SoftwareInputDTO request = new SoftwareInputDTO();
    mvc.perform(post("/api/software")
            .content(objectMapper.writeValueAsString(request))
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().is(400))
            .andExpect(jsonPath("$.fieldErrors", hasSize(1)))
            .andExpect(jsonPath("$.fieldErrors[?(@.field =~ /name/)].message").value("must not be empty"));
  }

  @Test
  public void givenCorrectRequest_whenAddingSoftware_thenReturnStatus200AndData() throws Exception {
    SoftwareInputDTO request = new SoftwareInputDTO();
    request.setName("Notepad ++");
    mvc.perform(post("/api/software")
            .content(objectMapper.writeValueAsString(request))
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().is(200))
            .andExpect(jsonPath("$.success").value(true))
            .andExpect(jsonPath("$.message").value("Utworzono oprogramowanie."));
  }

  @Test
  public void givenEmptyRequest_whenEditingSoftware_thenReturnStatus400() throws Exception {
    SoftwareInputDTO request = new SoftwareInputDTO();
    mvc.perform(put("/api/software/1")
            .content(objectMapper.writeValueAsString(request))
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().is(400))
            .andExpect(jsonPath("$.fieldErrors", hasSize(1)))
            .andExpect(jsonPath("$.fieldErrors[?(@.field =~ /name/)].message").value("must not be empty"));
  }

  @Test
  public void givenInvalidId_whenEditingSoftware_thenReturnStatus404() throws Exception {
    SoftwareInputDTO request = new SoftwareInputDTO();
    request.setName("Photoshop");
    mvc.perform(put("/api/software/0")
            .content(objectMapper.writeValueAsString(request))
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().is(404))
            .andExpect(jsonPath("$.success").value(false))
            .andExpect(jsonPath("$.message").value("Nie istnieje oprogramowanie o id: '0'"));
  }

  @Test
  public void givenNoId_whenEditingSoftware_thenReturnStatus405() throws Exception {
    mvc.perform(put("/api/software")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().is(405));
  }

  @Test
  public void givenCorrectRequest_whenEditingSoftware_thenReturnStatus200AndData() throws Exception {
    SoftwareInputDTO request = new SoftwareInputDTO();
    request.setName("Photoshop");
    mvc.perform(put("/api/software/1")
            .content(objectMapper.writeValueAsString(request))
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().is(200))
            .andExpect(jsonPath("$.success").value(true))
            .andExpect(jsonPath("$.message").value("Zaktualizowano parametry oprogramowania."));
  }

}
