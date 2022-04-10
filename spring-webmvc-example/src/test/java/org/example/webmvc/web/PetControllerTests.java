package org.example.webmvc.web;

import org.example.webmvc.config.root.RootConfig;
import org.example.webmvc.config.servlet.WebConfig;
import org.example.webmvc.domain.Pet;
import org.example.webmvc.service.PetService;
import org.example.webmvc.web.dto.PetRequest;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringJUnitWebConfig({RootConfig.class, WebConfig.class})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PetControllerTests {
    @Autowired
    WebApplicationContext wac;
    @Autowired
    PetService petService;

    MockMvc mockMvc;

    @BeforeEach
    void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac)
//                .addFilters(new CharacterEncodingFilter(StandardCharsets.UTF_8.toString(), true))
                .build();
    }

    @Test
    @Order(1)
    void testList() throws Exception {


        mockMvc.perform(get("/pets?page={page}&size={size}", 1, 10))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[?(@.length()> 0)]").exists());

    }

    @Test
    @Order(2)
    void testListAll() throws Exception {
        mockMvc.perform(get("/pets/all")).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[?(@.length()> 0)]").exists());
    }

    @Test
    @Order(0)
    void testRegister() throws Exception {

        PetRequest pet = PetRequest.of(
                "콜라",
                "정준하",
                "개",
                Pet.Sex.F,
                LocalDate.of(2004, 9, 7),
                LocalDate.of(2020, 11, 2)
        );

        ResultActions actions = mockMvc.perform(post("/register")
                .param("name", pet.getName())
                .param("owner", pet.getOwner())
                .param("species", pet.getSpecies())
                .param("sex", pet.getSex().name())
                .param("birth", pet.getBirth().toString())
                .param("death", pet.getDeath().toString())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        );

        actions.andExpect(status().isFound())
                .andExpect(redirectedUrl("/list"));
    }
}
