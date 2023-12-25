package com.locar;

import com.locar.dao.UtilisateurRepository;
import com.locar.entities.Utilisateur;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;

import java.util.List;

@SpringBootApplication
@Controller
@RequestMapping("/")
public class Main {
    private final UtilisateurRepository utilisateurRepository;

    public Main(UtilisateurRepository utilisateurRepository) {
        this.utilisateurRepository = utilisateurRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @GetMapping
    public String home(Model model) {
        List<Utilisateur> utilisateurs = utilisateurRepository.findAll();

        model.addAttribute("utilisateurs", utilisateurs);
        return "home";
    }


}


