package org.example.test.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.test.config.servlet.WebConfig;
import org.example.test.domain.Pet;
import org.example.test.web.dto.PetRequest;
import org.example.test.web.dto.PetRequestModel;
import org.example.test.web.dto.PetResponse;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.client.MockMvcClientHttpRequestFactory;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringJUnitWebConfig(WebConfig.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PetControllerTests {
    final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    WebApplicationContext wac;

    @Autowired
    ObjectMapper objectMapper;

    MockMvc mockMvc;
    RestTemplate restTemplate;

    Pet pet;

    @BeforeEach
    void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac)
                .addFilter(new CharacterEncodingFilter(StandardCharsets.UTF_8.toString(), true))
                .build();
        this.restTemplate = new RestTemplate(new MockMvcClientHttpRequestFactory(mockMvc));

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
    void testRegisterModel() {
        try {
            ResultActions actions = mockMvc.perform(
                    getParameter(get("/register"))
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .accept(MediaType.TEXT_HTML)
            );
            String qs = java.net.URLDecoder.decode(Optional.ofNullable(actions.andExpect(model().hasNoErrors()).andExpect(status().isOk()).andReturn().getRequest().getQueryString()).orElse(""), StandardCharsets.UTF_8.toString());
            logger.debug("query string: {}", qs);
        } catch (Exception e) {
            logger.error("testRegister");
        }
    }

    @Test
    @Order(1)
    void testRegisterProcessModel() throws Exception {
        ResultActions actions = mockMvc.perform(post("/register")
                .param("name", pet.getName())
                .param("owner", pet.getOwner())
                .param("species", pet.getSpecies())
                .param("sex", pet.getSex().name())
                .param("birth", pet.getBirth().toString())
                .param("death", pet.getDeath().toString())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                //.contentType(MediaType.APPLICATION_JSON)
        );

        logger.debug("mav: {}", actions.andExpect(model().hasNoErrors()).andReturn().getModelAndView());


    }

    @Test
    @Order(3)
    void testSendBody() {
        PetRequest petRequest = PetRequest.builder()
                .name("Emma")
                .owner("Carl Ballard")
                .species("개")
                .sex(Pet.Sex.F)
                .birth(LocalDate.of(2009, 1, 31))
                .build();

        try {
            String json = objectMapper.writeValueAsString(petRequest);
            logger.debug("json: {}", json);
            ResultActions actions = mockMvc.perform(
                    post("/send")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(json)
            );

            actions.andExpect(MockMvcResultMatchers.status().isOk());

        } catch (JsonProcessingException e) {
            logger.error("objectMapper.writeValueAsString(...) error");
        }catch (Exception e) {
            logger.error("testSend");
            throw new RuntimeException(e);
        }

    }

    @Test
    @Order(4)
    void testBody() {
        try {
            PetRequest petRequest = objectMapper.readValue("{\"name\":\"Emma\",\"owner\":\"Carl Ballard\",\"species\":\"개\",\"sex\":\"F\",\"birth\":\"2009-01-31\",\"death\":null}"
                                    , PetRequest.class);

            RequestEntity<PetRequest> httpEntity = RequestEntity
                    .method(HttpMethod.POST, "/body")
//                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(petRequest);

            ResponseEntity<?> entity = restTemplate.exchange(httpEntity, PetResponse.class);
            logger.debug("entity: {}", entity);

            ResultActions actions = mockMvc.perform(
                    post("/body")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("{\"name\":\"Emma\",\"owner\":\"Carl Ballard\",\"species\":\"개\",\"sex\":\"F\",\"birth\":\"2009-01-31\",\"death\":null}")
            );

            logger.debug("response: {}",
                    actions.andExpect(status().isOk()).andDo(print()).andReturn().getResponse().getContentAsString());


        } catch (Exception e) {
            logger.error("testBody");
            throw new RuntimeException(e);
        }
    }

    @Test
    @Order(5)
    void testModel() {
        try {
            PetRequest petRequest = objectMapper.readValue("{\"name\":\"Emma\",\"owner\":\"Carl Ballard\",\"species\":\"개\",\"sex\":\"F\",\"birth\":\"2009-01-31\",\"death\":null}"
                    , PetRequest.class);
            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            Map<String, String> map = objectMapper.convertValue(petRequest, new TypeReference<Map<String, String>>() {}); // (3)
            params.setAll(map);

            ResponseEntity<?> postForEntity = restTemplate.postForEntity(
                    "/model"
                    , RequestEntity.post("/model")
                            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                            .body(params)
                    , PetResponse.class);

            logger.debug("petRequestDto: {}", petRequest);
            logger.debug("postForEntity: {}", postForEntity);

            ResultActions actions = mockMvc.perform(
                    getParameter(get("/model"))
            );

            logger.debug("content: {}", actions.andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8));
        } catch (JsonProcessingException e) {
            logger.error("error on objectMapper.readValue(...)", e);
        } catch (Exception e) {
            logger.error("testModel", e);
        }
    }

    @Test
    @Order(6)
    void testPojoModel() {
        PetRequestModel petRequest = PetRequestModel.builder()
                .name("Emma")
                .owner("Carl Ballard")
                .species("개")
                .sex(Pet.Sex.F)
                .birth(LocalDate.of(2009, 1, 31))
                .build();

        Pet pet = petRequest.toEntity();

        try {
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
            );

            logger.debug("response: {}", actions.andReturn().getResponse().getContentAsString());

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @Order(7)
    void testPetRequestModel() {

        try {
            ResultActions actions = mockMvc.perform(
                    get("/pets/name/테스트/owner/김씨/species/개/sex/M")
                            .header(HttpHeaders.ACCEPT_CHARSET, StandardCharsets.UTF_8.toString())
//                            .accept(MediaType.APPLICATION_JSON)
                            .characterEncoding(StandardCharsets.UTF_8.toString())
                            .locale(Locale.KOREA)
//                            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
//                            .content("name=콜라&owner=김덕흥&species=개&sex=F&birth=2004-09-07&death=2020-11-02")
            );


            logger.debug("response: {}", actions.andReturn().getResponse().getContentAsString());

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @Order(8)
    void testNoResponseBody() {
        try {
            PetRequest petRequest = objectMapper.readValue("{\"name\":\"Emma\",\"owner\":\"Carl Ballard\",\"species\":\"개\",\"sex\":\"F\",\"birth\":\"2009-01-31\",\"death\":null}"
                    , PetRequest.class);

            RequestEntity<PetRequest> httpEntity = RequestEntity
                    .method(HttpMethod.POST, "/no")
//                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(petRequest);

            String object = restTemplate.postForObject("/no", httpEntity, String.class);
            logger.debug("object: {}", object);
        } catch (JsonProcessingException e) {
            logger.error("error on objectMapper.readValue(...)", e);
        }

    }

    private MockHttpServletRequestBuilder getParameter(MockHttpServletRequestBuilder builder) {
        return
                builder.queryParam("name", pet.getName())
                        .queryParam("owner", pet.getOwner())
                        .queryParam("species", pet.getSpecies())
                        .queryParam("sex", pet.getSex().name())
                        .queryParam("birth", pet.getBirth().toString())
                        .queryParam("death", pet.getDeath().toString());
    }
}
