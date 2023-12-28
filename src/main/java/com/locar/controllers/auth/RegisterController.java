package com.locar.controllers.auth;

import com.locar.entities.Utilisateur;
import com.locar.services.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/register")
public class RegisterController {

    @Autowired
    private UtilisateurService utilisateurService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public RegisterController(UtilisateurService utilisateurService, BCryptPasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
        this.utilisateurService = utilisateurService;
    }
    @GetMapping
    public String showSignupForm(Model model) {
        model.addAttribute("user", new Utilisateur());
        return "auth/register";
    }

    @PostMapping
    public String processSignup(Utilisateur utilisateur) {
        utilisateur.setPassword(passwordEncoder.encode(utilisateur.getPassword()));
        utilisateurService.save(utilisateur);
        return "redirect:/login";
    }

}