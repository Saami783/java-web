package com.locar;

import com.locar.dao.UtilisateurRepository;
import com.locar.dao.VehiculeRepository;
import com.locar.entities.Utilisateur;
import com.locar.entities.Vehicule;
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
    private final VehiculeRepository vehiculeRepository;

    public Main(VehiculeRepository vehiculeRepository) {
        this.vehiculeRepository = vehiculeRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @GetMapping
    public String home(Model model) {
        List<Vehicule> vehicules = vehiculeRepository.findAll();

        model.addAttribute("vehicules", vehicules);
        return "home";
    }


}


