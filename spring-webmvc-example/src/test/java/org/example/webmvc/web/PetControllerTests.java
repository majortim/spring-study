package org.example.webmvc.web;

import org.example.webmvc.config.root.RootConfig;
import org.example.webmvc.config.servlet.WebConfig;
import org.example.webmvc.domain.Pet;
import org.example.webmvc.service.PetService;
import org.example.webmvc.web.dto.PetRequestDto;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
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
import java.util.List;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@SpringJUnitWebConfig
//({RootConfig.class, WebConfig.class})
@ContextHierarchy({
        @ContextConfiguration(classes = RootConfig.class),
        @ContextConfiguration(classes = {WebConfig.class})
})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PetControllerTests {
    final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    WebApplicationContext wac;
    @Autowired
    PetService petService;

    MockMvc mockMvc;
    RestTemplate restTemplate;

    @BeforeEach
    void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac)
                .build();
        this.restTemplate = new RestTemplate(new MockMvcClientHttpRequestFactory(mockMvc));
    }

    @Test
    @Order(1)
    void testList() {
        logger.debug("list: {}", restTemplate.getForEntity("/pets?page={page}&size={size}", Map.class, 1, 10));


        ResponseEntity<?> entity = restTemplate.getForEntity("/pets", Map.class);
        logger.debug("all: {}", entity);
        Object body = entity.getBody();

        if (body instanceof Map) {
            Map<?, ?> map = ((Map<?, ?>) body);
            Object list = map.get("list");
            if (list instanceof List) {
                logger.debug("count: {}", ((List<?>) list).size());
            }
        }
    }

    @Test
    @Order(2)
    void testListAll() {
        try {
            ResponseEntity<?> entity = restTemplate.getForEntity("/pets/all", List.class);
            logger.debug("all: {}", entity);
            Object body = entity.getBody();

            if (body instanceof List) {
                logger.debug("count: {}", ((List<?>) body).size());
            }
        } catch (Exception e) {
            logger.error("testListAll");
            throw e;
        }
    }

    @Test
    @Order(3)
    void testTest2() {
        try {
            ResponseEntity<?> entity = restTemplate.getForEntity("/pets/test2?name=Emma&owner=Carl Ballard", List.class);
            logger.debug("test2: {}", entity);

        } catch (Exception e) {
            logger.error("testTest2");
            throw e;
        }
    }

    @Test
    @Order(4)
    void testTest3() {
        try {
            PetRequestDto petRequestDto = new PetRequestDto();
//                    new PetRequestDto("Emma", "Carl Ballard", "개", Pet.Sex.F
//                    , LocalDate.of(2009, 1, 31), null);


            RequestEntity<PetRequestDto> httpEntity = RequestEntity
                    .method(HttpMethod.GET, "/pets/test3")
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(petRequestDto);

            ResponseEntity<?> entity = restTemplate.exchange(httpEntity, List.class);
            logger.debug("test3: {}", entity);

        } catch (Exception e) {
            logger.error("testTest3 error");
            throw e;
        }
    }

    @Test
    @Order(0)
    void testRegister() throws Exception {

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
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        );

        actions.andExpect(model().hasNoErrors());

        MockHttpServletResponse response = actions.andReturn().getResponse();
        logger.debug("result: {}", response);


    }
}
