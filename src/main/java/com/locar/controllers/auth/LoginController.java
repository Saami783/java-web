package com.locar.controllers.auth;

import com.locar.entities.Utilisateur;
import com.locar.services.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class LoginController {

    @Autowired
    private UtilisateurService utilisateurService;

    public LoginController(UtilisateurService utilisateurService) {
        this.utilisateurService = utilisateurService;
    }

    @GetMapping("/login")
    public String login(Principal principal) {
        if (principal != null) {
            Utilisateur utilisateur = this.utilisateurService.findByEmail(principal.getName());
            if (utilisateur != null) {
                return "redirect:/";
            }
        }
        return "auth/login";
    }
}

