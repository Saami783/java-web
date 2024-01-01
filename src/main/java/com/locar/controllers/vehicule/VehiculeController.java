package com.locar.controllers.vehicule;

import com.locar.entities.Reservation;
import com.locar.entities.Vehicule;
import com.locar.services.VehiculeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/vehicules")
public class VehiculeController {

    private final VehiculeService vehiculeService;

    public VehiculeController(VehiculeService vehiculeService) {
        this.vehiculeService = vehiculeService;
    }

    @GetMapping
    public String home(Model model) {
        List<Vehicule> vehicules = vehiculeService.findAll();
        model.addAttribute("vehicules", vehicules);
        return "vehicules/index";
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        Optional<Vehicule> vehiculeOpt = vehiculeService.findById(id);
        if (vehiculeOpt.isPresent()) {
            Reservation reservation = new Reservation();
            reservation.setVehicule(vehiculeOpt.get());
            model.addAttribute("vehicule", vehiculeOpt.get());
            model.addAttribute("reservation", reservation);
            return "vehicules/detail";
        } else {
            return "vehicules/index";
        }
    }

}
