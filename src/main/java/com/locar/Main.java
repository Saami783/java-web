package com.locar;

import com.locar.dao.UtilisateurRepository;
import com.locar.dao.VehiculeRepository;
import com.locar.entities.Utilisateur;
import com.locar.entities.Vehicule;
import com.locar.services.VehiculeService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;

import java.util.List;

@SpringBootApplication
@Controller
@RequestMapping("/")
public class Main {
    private final VehiculeService vehiculeService;

    public Main(VehiculeService vehiculeService) {
        this.vehiculeService = vehiculeService;
    }

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @GetMapping
    public String home(Model model) {
        List<Vehicule> vehicules = vehiculeService.findAll();
        if (vehicules.size() > 3) {
            vehicules = vehicules.subList(0, 3);
        }
        model.addAttribute("vehicules", vehicules);
        return "home";
    }


}


