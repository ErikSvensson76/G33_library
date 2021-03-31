package se.lexicon.jpa_workshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import se.lexicon.jpa_workshop.data.AppUserDAO;
import se.lexicon.jpa_workshop.model.dto.AppUserDTO;
import se.lexicon.jpa_workshop.model.dto.AppUserDTOForm;
import se.lexicon.jpa_workshop.service.AppUserService;

import javax.validation.Valid;

@Controller
public class AppUserController {

    private final AppUserService appUserService;
    private final AppUserDAO appUserDAO;

    @Autowired
    public AppUserController(AppUserService appUserService, AppUserDAO appUserDAO) {
        this.appUserService = appUserService;
        this.appUserDAO = appUserDAO;
    }

    @GetMapping("/user/register")
    public String getAppUserForm(Model model){
        AppUserDTOForm form = new AppUserDTOForm();
        model.addAttribute("form", form);

        return "user_register";
    }

    @PostMapping("/user/register/process")
    public String processForm(
            @Valid @ModelAttribute("form") AppUserDTOForm form,
            BindingResult bindingResult,
            @RequestParam(name = "confirmation") String confirmation
            ){

        if(!form.getPassword().equals(confirmation)){
            FieldError fieldError = new FieldError("form", "confirmation", "Confirmation did not match password");
            bindingResult.addError(fieldError);
        }

        /*
        if(appUserDAO.findByUsername(form.getUsername().trim()) != null){
            FieldError fieldError = new FieldError("form", "username", "Username " + form.getUsername()+ " is taken");
            bindingResult.addError(fieldError);
        }

        if(appUserDAO.findByEmail(form.getEmail().trim()) != null){
            FieldError fieldError = new FieldError("form", "username", "Email " + form.getEmail() + " is taken");
            bindingResult.addError(fieldError);
        }

         */

        if(bindingResult.hasFieldErrors()){
            return "user_register";
        }

        int userId = appUserService.create(form);
        return "redirect:/user/"+userId;
    }

    @GetMapping("/user/{id}")
    public String findById(@PathVariable("id") int id, Model model){
        AppUserDTO appUserDTO = appUserService.findById(id);
        model.addAttribute("user", appUserDTO);
        return "user_view";
    }
}
