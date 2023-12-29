package com.locar.controllers.reservation;

import com.locar.dao.ReservationRepository;
import com.locar.entities.Reservation;
import com.locar.entities.Tarif;
import com.locar.entities.Utilisateur;
import com.locar.entities.Vehicule;
import com.locar.services.ReservationService;
import com.locar.services.UtilisateurService;
import com.locar.services.VehiculeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.beans.PropertyEditorSupport;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.security.Principal;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/reservations")
public class ReservationController {
    private final ReservationService reservationService;
    private final VehiculeService vehiculeService;
    private final UtilisateurService utilisateurService;
    public ReservationController(ReservationService reservationService, VehiculeService vehiculeService, UtilisateurService utilisateurService) {
        this.reservationService = reservationService;
        this.vehiculeService = vehiculeService;
        this.utilisateurService = utilisateurService;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new PropertyEditorSupport() {
            public void setAsText(String value) {
                try {
                    setValue(new SimpleDateFormat("yyyy-MM-dd").parse(value));
                } catch (ParseException e) {
                    setValue(null);
                }
            }
        });
    }

    @GetMapping
    public String index(Model model, Principal principal) {
        if (principal != null) {

            Utilisateur utilisateur = utilisateurService.findByEmail(principal.getName());

            List<Reservation> reservations = reservationService.findByUtilisateurId(utilisateur.getId());

            model.addAttribute("reservations", reservations);
        }
        return "reservations/index";
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable Long id, Model model) {
        Optional<Reservation> reservation = reservationService.findById(id);
        if (reservation.isPresent()) {
            model.addAttribute("reservation", reservation.get());
            return "reservations/detail";
        } else {
            return "redirect:/reservations";
        }
    }

    @PostMapping("/create")
    public String create(@ModelAttribute Reservation reservation, Model model, Principal principal, RedirectAttributes redirectAttributes) {

        Utilisateur utilisateur = utilisateurService.findByEmail(principal.getName());

        Vehicule vehicule = vehiculeService.findById(reservation.getVehicule().getId()).orElseThrow();
        Long vehiculeId = reservation.getVehicule().getId();

        try {

            if (!utilisateur.isVerified() || !utilisateur.isVerifiedByAdmin()) {
                redirectAttributes.addFlashAttribute("error", "Votre compte doit être vérifié pour effectuer cette action.");
                return "redirect:/vehicules/" + vehiculeId;
            }
            List<Tarif> tarifs = vehiculeService.getTarifs(vehicule);

            LocalDate debut = reservation.getDateDebutReservation().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate fin = reservation.getDateFinReservation().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

            long nombreDeJours = ChronoUnit.DAYS.between(debut, fin);
            reservation.setNbJourReserve((int) nombreDeJours);

            float prix = calculerPrix(tarifs, reservation.getNbJourReserve());
            reservation.setPrix(prix);
            reservation.setUtilisateur(utilisateur);
            reservation.setVehicule(vehicule);
            reservation.setFacture(null);

            reservationService.save(reservation);

            return "redirect:/reservations";

        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Une erreur est survenue lors de " +
                    "la création de la réservation: " + e.getMessage());
            return "redirect:/vehicules/" + vehiculeId;
        }
    }

    private float calculerPrix(List<Tarif> tarifs, int nbJours) {

        float prixJour = 0;
        float prixHebdo = 0;
        float prixMensuel = 0;

        for (Tarif tarif : tarifs) {
            switch (tarif.getCalendrier()) {
                case "Jour":
                    prixJour = tarif.getPrix();
                    break;
                case "Hebdomadaire":
                    prixHebdo = tarif.getPrix();
                    break;
                case "Mensuel":
                    prixMensuel = tarif.getPrix();
                    break;
            }
        }

        // Calcul du prix en fonction du nombre de jours
        if (nbJours >= 22) {
            return prixMensuel;
        } else if (nbJours >= 15) {
            return prixHebdo * 3;
        } else if (nbJours >= 8) {
            return prixHebdo * 2;
        } else if (nbJours == 7) {
            return prixHebdo;
        } else {
            return prixJour * nbJours;
        }
    }

}
