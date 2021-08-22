package org.example.webmvc.web;

import org.example.webmvc.config.RootConfig;
import org.example.webmvc.config.WebConfig;
import org.example.webmvc.domain.Pet;
import org.example.webmvc.service.PetService;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.client.MockMvcClientHttpRequestFactory;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringJUnitWebConfig
//({RootConfig.class, WebConfig.class})
@ContextHierarchy({
        @ContextConfiguration(classes = RootConfig.class),
        @ContextConfiguration(classes = WebConfig.class)
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
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
        this.restTemplate = new RestTemplate(new MockMvcClientHttpRequestFactory(mockMvc));
    }

    @Test
    @Order(0)
    void testSave() {
        Pet pet1 = Pet.builder()
                .name("뚱식이")
                .owner("문세윤")
                .species("챠우챠우")
                .sex(Pet.Sex.M)
                .birth(LocalDate.of(2019, 3, 2))
                //.death(LocalDate.of(2020, 11,2))
                .build();
        Pet pet2 = Pet.builder()
                .name("복순이")
                .owner("김태리")
                .species("콜리")
                .sex(Pet.Sex.F)
                .birth(LocalDate.of(2004, 9, 7))
                .death(LocalDate.of(2020, 11, 2))
                .build();

        petService.save(pet1);
        petService.save(pet2);
    }

    @Test
    @Order(2)
    public void testList() {
        logger.debug("list: {}", restTemplate.getForEntity("/pets?page={page}&size={size}", Map.class, 1, 10));


        ResponseEntity<?> entity = restTemplate.getForEntity("/pets", Map.class);
        logger.debug("all: {}", entity);
        Object body = entity.getBody();

        if(body instanceof Map) {
            Map<?, ?> map = ((Map<?, ?> ) body);
            Object list = map.get("list");
            if(list instanceof List){
                logger.debug("count: {}", ((List<?>) list).size());
            }
        }
    }

    @Test
    @Order(3)
    public void testListAll() {
        try {
            ResponseEntity<?> entity = restTemplate.getForEntity("/pets/all", List.class);
            logger.debug("all: {}", entity);
            Object body = entity.getBody();

            if(body instanceof List) {
                logger.debug("count: {}", ((List<?>) body).size());
            }
        } catch (Exception e) {
            logger.error("testListAll");
            throw e;
        }
    }

    @Test
    @Order(1)
    void testRegister() throws Exception {

        Pet pet = Pet.builder()
                .name("뽕순이")
                .owner("김태희")
                .species("콜리")
                .sex(Pet.Sex.F)
                .birth(LocalDate.of(2004, 9, 7))
                .death(LocalDate.of(2020, 11, 2))
                .build();

        Object result = mockMvc.perform(post("/register")
                    .param("name", pet.getName())
                    .param("owner", pet.getOwner())
                    .param("species", pet.getSpecies())
                    .param("sex", pet.getSex().name())
                    .param("birth", pet.getBirth().toString())
                    .param("death", pet.getDeath().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                ).andReturn().getResponse();
        logger.debug("result: {}", result);

    }
}
