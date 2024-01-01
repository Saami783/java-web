package com.locar.controllers.vehicule;

import com.locar.dao.VehiculeRepository;
import com.locar.entities.Avis;
import com.locar.entities.Reservation;
import com.locar.entities.Vehicule;
import com.locar.services.AvisService;
import com.locar.services.VehiculeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/vehicules")
public class VehiculeController {

    @Autowired
    private final VehiculeService vehiculeService;
    @Autowired
    private final AvisService avisService;
    public VehiculeController(VehiculeService vehiculeService, AvisService avisService) {
        this.vehiculeService = vehiculeService;
        this.avisService = avisService;
    }

    @GetMapping
    public String home(Model model) {
        List<Vehicule> vehicules = vehiculeService.findAll();
        model.addAttribute("vehicules", vehicules);
        return "vehicules/index";
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable Long id, Model model) {
        Optional<Vehicule> vehiculeOpt = vehiculeService.findById(id);

        if (vehiculeOpt.isPresent()) {
            List<Avis> avisList = avisService.findAvisByVehiculeId(id);

            Reservation reservation = new Reservation();
            Avis avisForm = new Avis();
            reservation.setVehicule(vehiculeOpt.get());

            model.addAttribute("vehicule", vehiculeOpt.get());
            model.addAttribute("reservation", reservation);
            model.addAttribute("avisForm", avisForm);
            model.addAttribute("avisList", avisList);

            return "vehicules/detail";
        } else {
            return "vehicules/index";
        }
    }


}
