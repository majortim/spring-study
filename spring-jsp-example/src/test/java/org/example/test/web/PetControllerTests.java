package org.example.test.web;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.test.config.servlet.WebConfig;
import org.example.test.domain.Pet;
import org.example.test.web.dto.PetRequest;
import org.example.test.web.dto.PetRequestModel;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.Locale;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringJUnitWebConfig(WebConfig.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class
PetControllerTests {
    final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    WebApplicationContext wac;

    @Autowired
    ObjectMapper objectMapper;

    MockMvc mockMvc;

    Pet pet;

    @BeforeEach
    void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac)
                .build();
        this.pet = Pet.builder()
                .name("콜라")
                .owner("김덕흥")
                .species("개")
                .sex(Pet.Sex.F)
                .birth(LocalDate.of(2004, 9, 7))
                .death(LocalDate.of(2020, 11, 2))
                .build();
    }

    @Test
    @Order(0)
    void registerModelTest() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = get("/register")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .accept(MediaType.TEXT_HTML);
        setParameter(requestBuilder);

        mockMvc.perform(requestBuilder).andExpect(status().isOk());
    }

    @Test
    @Order(1)
    void testRegisterProcessModel() throws Exception {
        ResultActions actions = mockMvc.perform(post("/process")
                        .param("name", pet.getName())
                        .param("owner", pet.getOwner())
                        .param("species", pet.getSpecies())
                        .param("sex", pet.getSex().name())
                        .param("birth", pet.getBirth().toString())
                        .param("death", pet.getDeath().toString())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                //.contentType(MediaType.APPLICATION_JSON)
        );

        actions.andExpect(status().isOk());
    }

    @Test
    @Order(3)
    void testSendBody() throws Exception {
        PetRequest petRequest = PetRequest.builder()
                .name("Emma")
                .owner("Carl Ballard")
                .species("개")
                .sex(Pet.Sex.F)
                .birth(LocalDate.of(2009, 1, 31))
                .build();

        ResultActions actions = mockMvc.perform(
                post("/send")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(petRequest))
        );

        actions.andExpect(status().isOk());
    }

    @Test
    @Order(4)
    void testBody() throws Exception {
        PetRequest petRequest = PetRequest.builder()
                .name("Emma")
                .owner("Carl Ballard")
                .species("개")
                .sex(Pet.Sex.F)
                .birth(LocalDate.of(2009, 1, 31))
                .build();

        ResultActions actions = mockMvc.perform(
                post("/body")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(petRequest))
        );

        actions.andExpect(status().isOk());

    }

    @Test
    @Order(5)
    void testModel() throws Exception {
        PetRequest petRequest = getTestData();
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        Map<String, String> map = objectMapper.convertValue(petRequest, new TypeReference<>() {
        }); // (3)
        params.setAll(map);

        MockHttpServletRequestBuilder requestBuilder = get("/model");
        setParameter(requestBuilder);

        mockMvc.perform(requestBuilder).andExpect(status().isOk());
        mockMvc.perform(get("/model")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .params(params)).andExpect(status().isOk());


    }

    @Test
    @Order(6)
    void testPojoModel() throws Exception {
        PetRequestModel pet = PetRequestModel.builder()
                .name("Emma")
                .owner("Carl Ballard")
                .species("개")
                .sex(Pet.Sex.F)
                .birth(LocalDate.of(2009, 1, 31))
                .death(LocalDate.of(2016, 12, 12))
                .build();

        ResultActions actions = mockMvc.perform(
                post("/pojo")
                        .header(HttpHeaders.ACCEPT_CHARSET, StandardCharsets.UTF_8.toString())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .characterEncoding(StandardCharsets.UTF_8.toString())
                        .accept(MediaType.APPLICATION_JSON)
                        .locale(Locale.KOREA)
                        .param("name", pet.getName())
                        .param("owner", pet.getOwner())
                        .param("species", pet.getSpecies())
                        .param("sex", pet.getSex().name())
                        .param("birth", pet.getBirth().toString())
                        .param("death", pet.getDeath().toString())
        );

        logger.debug("response: {}", actions.andReturn().getResponse().getContentAsString());

        actions.andExpect(status().isOk());
    }

    @Test
    @Order(7)
    void testPetRequestModel() throws Exception {

        ResultActions actions = mockMvc.perform(
                get("/pets/name/테스트/owner/김씨/species/개/sex/M")
                        .header(HttpHeaders.ACCEPT_CHARSET, StandardCharsets.UTF_8.toString())
                            .accept(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.ACCEPT_CHARSET, StandardCharsets.UTF_8)
                        .characterEncoding(StandardCharsets.UTF_8.toString())
                        .locale(Locale.KOREA)
//                            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
//                            .content("name=콜라&owner=김덕흥&species=개&sex=F&birth=2004-09-07&death=2020-11-02")
        );

        actions.andExpect(status().isOk());
    }

    @Test
    @Order(8)
    void testNoResponseBody() throws Exception {
        PetRequest petRequest = getTestData();

        ResultActions actions = mockMvc.perform(post("/no")
                .header(HttpHeaders.ACCEPT_CHARSET, StandardCharsets.UTF_8.toString())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(petRequest))
                .accept(MediaType.TEXT_PLAIN)
                .characterEncoding(StandardCharsets.UTF_8.toString())
                .locale(Locale.KOREA)
        );

        actions.andExpect(status().isOk());


    }

    private void setParameter(MockHttpServletRequestBuilder builder) {
        builder.param("name", pet.getName())
                .param("owner", pet.getOwner())
                .param("species", pet.getSpecies())
                .param("sex", pet.getSex().name())
                .param("birth", pet.getBirth().toString())
                .param("death", pet.getDeath().toString());
    }

    private PetRequest getTestData() {
        return PetRequest.builder()
                .name("Emma")
                .owner("Carl Ballard")
                .species("개")
                .sex(Pet.Sex.F)
                .birth(LocalDate.of(2009, 1, 31))
                .build();
    }
}
