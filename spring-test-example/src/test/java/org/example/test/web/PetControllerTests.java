package org.example.test.web;

import org.example.test.config.servlet.WebConfig;
import org.example.test.domain.Pet;
import org.example.test.web.dto.PetRequestDto;
import org.example.test.web.dto.PetResponseDto;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.client.MockMvcClientHttpRequestFactory;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringJUnitWebConfig
//({RootConfig.class, WebConfig.class})
@ContextHierarchy({
        @ContextConfiguration(classes = {WebConfig.class})
})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PetControllerTests {
    final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    WebApplicationContext wac;

    MockMvc mockMvc;
    RestTemplate restTemplate;

    @BeforeEach
    void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac)
                .build();
        this.restTemplate = new RestTemplate(new MockMvcClientHttpRequestFactory(mockMvc));
    }

    @Test
    @Order(0)
    void testRegister() {
        try {
            PetRequestDto petRequestDto = restTemplate.getForObject("/register?name=Emma&owner=Carl Ballard", PetRequestDto.class);
            logger.debug("testRegister: {}", petRequestDto);

        } catch (Exception e) {
            logger.error("testRegister");
            throw e;
        }
    }

    @Test
    @Order(1)
    void testRegisterProcess() throws Exception {

        Pet pet = Pet.builder()
                .name("콜라")
                .owner("정준하")
                .species("개")
                .sex(Pet.Sex.F)
                .birth(LocalDate.of(2004, 9, 7))
                .death(LocalDate.of(2020, 11, 2))
                .build();

        ResultActions actions = mockMvc.perform(post("/register")
                .param("name", pet.getName())
                .param("owner", pet.getOwner())
                .param("species", pet.getSpecies())
                .param("sex", pet.getSex().name())
                .param("birth", pet.getBirth().toString())
                .param("death", pet.getDeath().toString())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                //.contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        );

//        actions.andExpect(MockMvcResultMatchers.status().isOk());
//        actions.andExpect(model().hasNoErrors());
        String result = actions.andReturn().getResponse().getContentAsString();
        logger.debug("result: {}", result);
        Assertions.assertNotNull(result);

        MockHttpServletResponse response = actions.andReturn().getResponse();
        logger.debug("result: {}", response);
    }

    @Test
    @Order(2)
    void testBody() {
        try {
            PetRequestDto petRequestDto = new PetRequestDto();
//                    new PetRequestDto("Emma", "Carl Ballard", "개", Pet.Sex.F
//                    , LocalDate.of(2009, 1, 31), null);


            RequestEntity<PetRequestDto> httpEntity = RequestEntity
                    .method(HttpMethod.GET, "/body")
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(petRequestDto);

            ResponseEntity<?> entity = restTemplate.exchange(httpEntity, PetResponseDto.class);
            logger.debug("testBody: {}", entity);

        } catch (Exception e) {
            logger.error("testBody");
            throw e;
        }
    }
}
