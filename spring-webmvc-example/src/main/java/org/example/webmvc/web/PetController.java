package org.example.webmvc.web;

import org.example.webmvc.domain.Pet;
import org.example.webmvc.service.PetService;
import org.example.webmvc.web.dto.PetRequestDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;


@Controller
public class PetController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final PetService petService;

    public PetController(PetService petService) {
        this.petService = petService;
    }

    @GetMapping("/list")
    public String list() {
        return "list";
    }

    @GetMapping("/register")
    public String register(@ModelAttribute("pet") PetRequestDto petRequestDto) {
        return "register";
    }

    @PostMapping("/register")
    public String registerProcess(@Valid @ModelAttribute("pet") PetRequestDto petRequestDto, BindingResult result) {
        if (result.hasErrors()) {
            return "register";
        }
        Pet saved = petService.save(petRequestDto.toEntity());
        logger.debug("saved: {}", saved);
        return "redirect:/list";

    }

    @GetMapping("/pets/{petId}/edit")
    public String initUpdateForm(@PathVariable("petId") Pet pet, ModelMap model) {

        if (pet != null)
            model.put("pet", pet);

        return "register";
    }

    @PostMapping("/pets/{petId}/edit")
    public String processUpdateForm(@PathVariable("petId") Pet pet, @Valid @ModelAttribute("pet") PetRequestDto petRequestDto, BindingResult result) {
        if (result.hasErrors()) {
            return "register";
        }

        if (pet != null) {
            pet.update(petRequestDto.toEntity());
            petService.save(pet);
        }

        return "redirect:/list";
    }

    @PostMapping("/pets/{petId}/delete")
    public String delete(@PathVariable("petId") long petId) {
        petService.deleteById(petId);

        return "redirect:/list";
    }
}
