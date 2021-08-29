package org.example.webmvc.web;

import org.example.webmvc.domain.Pet;
import org.example.webmvc.service.PetService;
import org.example.webmvc.web.dto.PetRequestDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;


@Controller
public class PetController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final PetService petService;

    public PetController(PetService petService) {
        this.petService = petService;
    }

    @GetMapping("/list")
    public String pets() {
        return "pets";
    }

    @GetMapping("/register")
    public String register(@ModelAttribute("pet") PetRequestDto petRequestDto) {
        return "register";
    }

    @PostMapping("/register")
    public String registerProcess(@Valid PetRequestDto petRequestDto , BindingResult result) {
        try{
            if(result.hasErrors()) {
                return "register";
            }
            Pet saved = petService.save(petRequestDto.toEntity());
            logger.debug("saved: {}", saved);
            return "redirect:/list";
        } catch (RuntimeException e) {
            logger.error(e.getClass().getName(), e);
            throw e;
        }
    }
}
