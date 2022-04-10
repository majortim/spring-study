package org.example.test.web;

import org.example.test.web.dto.PetRequest;
import org.example.test.web.dto.PetRequestModel;
import org.example.test.web.dto.PetResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Locale;
import java.util.Optional;

@Controller
public class PetController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private static final String TEST_PAGE = "test";
    private static final String REDIRECT_REGISTER = "redirect:register";

    @GetMapping("/")
    public String home() {
        return REDIRECT_REGISTER;
    }

    @GetMapping("/register")
    public String register(@ModelAttribute("pet") PetRequestModel petRequestModel) {
        return TEST_PAGE;
    }


    @PostMapping("/process")
    public String registerProcess(@Valid @ModelAttribute("pet") PetRequestModel petRequestModel, BindingResult result) {

        if (result.hasErrors()) {
            throw new RuntimeException(Optional.ofNullable(result.getFieldError())
                    .map(DefaultMessageSourceResolvable::getDefaultMessage).orElse(""));
        }

        return TEST_PAGE;
    }

    @RequestMapping(value = "/send", method = {RequestMethod.GET, RequestMethod.POST})
    public String sendWithRequestBodyForm(@Valid @RequestBody PetRequest petRequest, BindingResult result) {

        if (result.hasErrors()) {
            throw new RuntimeException(Optional.ofNullable(result.getFieldError())
                    .map(DefaultMessageSourceResolvable::getDefaultMessage).orElse(""));
        }

        logger.debug("petRequest: {}", petRequest);

        return TEST_PAGE;
    }

    @RequestMapping(value = "/body", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseEntity<?> sendWithRequestBody(
            @Valid @RequestBody PetRequest petRequest
            , BindingResult result
    ) {

        if (result.hasErrors()) {
            return ResponseEntity.internalServerError()
                    .body(Optional.ofNullable(result.getFieldError())
                            .map(DefaultMessageSourceResolvable::getDefaultMessage));
        }

        logger.debug("petRequest: {}", petRequest);

        PetResponse petResponse = new PetResponse(petRequest.toEntity());

        return ResponseEntity
                .status(HttpStatus.OK)
                .header(HttpHeaders.CONTENT_LANGUAGE, Locale.KOREA.toLanguageTag())
                .contentType(MediaType.APPLICATION_JSON)
                .body(petResponse);
    }

    @RequestMapping(value = "/model", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseEntity<?> model(@ModelAttribute PetRequest petRequest) {
        PetResponse petResponse = new PetResponse(petRequest.toEntity());

        return ResponseEntity
                .status(HttpStatus.OK)
                .header(HttpHeaders.CONTENT_LANGUAGE, Locale.KOREA.toLanguageTag())
                .contentType(MediaType.APPLICATION_JSON)
                .body(petResponse);
    }

    @ResponseBody
    @RequestMapping(value = "/pojo", method = {RequestMethod.GET, RequestMethod.POST})
    public PetResponse pojo(@Valid @ModelAttribute PetRequestModel petRequestModel) {

        return new PetResponse(petRequestModel.toEntity());
    }

    @SuppressWarnings("unconsumed")
    @ResponseBody
    @GetMapping(value = "/pets/name/{name}/owner/{owner}/species/{species}/sex/{sex}")
    public PetResponse pet(@ModelAttribute("pet") PetRequestModel petRequestModel) {
        logger.debug("{}", petRequestModel);

        return new PetResponse(petRequestModel.toEntity());
    }

    @RequestMapping(value = "/no", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseEntity<String> noResponse(@RequestBody PetRequest petRequest) {
        logger.debug("{}", petRequest);
        return ResponseEntity.ok().body("no");
    }
}
