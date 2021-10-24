package org.example.test.web;

import org.example.test.web.dto.PetRequestDto;
import org.example.test.web.dto.PetResponseDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Locale;

@Controller
public class PetController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping("/register")
    public String register(@ModelAttribute PetRequestDto petRequestDto) {
        logger.debug("petRequestDto: {}", petRequestDto);
        return "test";
    }


    @PostMapping("/register")
    public String registerProcess(@Valid @ModelAttribute PetRequestDto petRequestDto, BindingResult result) {
        if (result.hasErrors()) {
            throw new RuntimeException();
        }

        logger.debug("petRequestDto: {}", petRequestDto);
        return "test";
    }

    @RequestMapping(value = "/send", method = {RequestMethod.GET, RequestMethod.POST})
    public String sendWithRequestBodyForm(@Valid @RequestBody PetRequestDto petRequestDto, BindingResult result) {

        if (result.hasErrors()) {
            throw new RuntimeException();
        }

        logger.debug("requestBody: {}", petRequestDto);

        return "test";
    }

    @ResponseBody
    @RequestMapping(value = "/body", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseEntity<PetResponseDto> sendWithRequestBody(@Valid @RequestBody PetRequestDto petRequestDto, BindingResult result) {
        HttpStatus httpStatus = HttpStatus.OK;

        if (result.hasErrors()) {
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        PetResponseDto petResponseDto = new PetResponseDto(petRequestDto.toEntity());

        logger.debug("requestBody: {}", petRequestDto);
        logger.debug("petResponseDto: {}", petResponseDto);

        return ResponseEntity
                .status(httpStatus)
                .header(HttpHeaders.CONTENT_LANGUAGE, Locale.KOREA.toLanguageTag())
                .contentType(MediaType.APPLICATION_JSON)
                .body(petResponseDto);
    }
}
