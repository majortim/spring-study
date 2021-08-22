package org.example.webmvc.web;

import org.example.webmvc.domain.Pet;
import org.example.webmvc.service.PetService;
import org.example.webmvc.web.dto.PetDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

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
    public String register() {
        return "register";
    }

    @PostMapping("/register")
    public String update(PetDto petDto, BindingResult result, Model model) {
        try{
            Pet saved = petService.save(petDto.toEntity());
            logger.debug("saved: {}", saved);
            return "redirect:/pets";
        } catch (RuntimeException e){
            logger.error("RuntimeException", e);
            return "register";
        }
    }
}
