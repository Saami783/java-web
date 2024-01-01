package com.locar.controllers.reservation;

import com.locar.entities.Reservation;
import com.locar.entities.Tarif;
import com.locar.entities.Utilisateur;
import com.locar.entities.Vehicule;
import com.locar.services.ReservationService;
import com.locar.services.UtilisateurService;
import com.locar.services.VehiculeService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.beans.PropertyEditorSupport;
import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Objects;
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

            Utilisateur utilisateur = this.utilisateurService.findByEmail(principal.getName());

            List<Reservation> reservations = this.reservationService.findByUtilisateurId(utilisateur.getId());

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

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes, Principal principal) {
        try {
            Optional<Reservation> reservation = this.reservationService.findById(id);
            Utilisateur utilisateur = this.utilisateurService.findByEmail(principal.getName());

            if (reservation.isPresent()) {
                if (!Objects.equals(reservation.get().getUtilisateur().getId(), utilisateur.getId())) {
                    redirectAttributes.addFlashAttribute("error", "Impossible de modifier cette réservation");
                    return "redirect:/reservations";
                } else if (reservation.get().getFacture() != null) {
                    redirectAttributes.addFlashAttribute("error", "Impossible de modifier une " + "réservation déjà payée.");
                    return "redirect:/reservations";
                }
                model.addAttribute("reservation", reservation.get());
                return "reservations/edit";
            } else {
                redirectAttributes.addFlashAttribute("error", "Impossible de modifier cette réservation");
                return "redirect:/reservations";
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Une erreur est survenue lors de la modification de la réservation");
            return "redirect:/reservations";
        }
    }

    @PostMapping("edit/{id}")
    public String update(@PathVariable Long id, @ModelAttribute("reservation") Reservation updatedReservation, Principal principal, RedirectAttributes redirectAttributes) {
        Utilisateur utilisateur = this.utilisateurService.findByEmail(principal.getName());
        Optional<Reservation> reservationOptional = this.reservationService.findById(id);

        if (reservationOptional.isPresent()) {
            Reservation reservation = reservationOptional.get();

            if (!Objects.equals(reservation.getUtilisateur().getId(), utilisateur.getId())) {
                redirectAttributes.addFlashAttribute("error", "Vous n'êtes pas autorisé à modifier cette réservation.");
                return "redirect:/reservations";
            }

            reservation.setDateDebutReservation(updatedReservation.getDateDebutReservation());
            reservation.setDateFinReservation(updatedReservation.getDateFinReservation());

            LocalDate debut = reservation.getDateDebutReservation().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate fin = reservation.getDateFinReservation().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            long nombreDeJours = ChronoUnit.DAYS.between(debut, fin);
            reservation.setNbJourReserve((int) nombreDeJours);

            Vehicule vehicule = reservation.getVehicule();
            List<Tarif> tarifs = this.vehiculeService.getTarifs(vehicule);
            float prix = calculerPrix(tarifs, reservation.getNbJourReserve());

            if (prix == 0) {
                redirectAttributes.addFlashAttribute("error", "Vous ne pouvez pas louer un véhicule au delà de 31 jours");
                return "redirect:/reservations/edit/" + reservationOptional.get().getId();
            }

            reservation.setPrix(prix);

            this.reservationService.save(reservation);

            redirectAttributes.addFlashAttribute("success", "Réservation mise à jour avec succès");
            return "redirect:/reservations";
        } else {
            redirectAttributes.addFlashAttribute("error", "Réservation introuvable");
            return "redirect:/reservations";
        }
    }


    @GetMapping("/delete/{id}")
    @Transactional
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes, Principal principal) {
        Optional<Reservation> reservation = this.reservationService.findById(id);
        Utilisateur utilisateur = this.utilisateurService.findByEmail(principal.getName());
        try {
            if (!Objects.equals(reservation.get().getUtilisateur().getId(), utilisateur.getId())) {
                redirectAttributes.addFlashAttribute("error", "Vous ne pouvez pas supprimer " + "une réservation qui n'est pas la vôtre.");
                return "redirect:/reservations";
            }
            if (reservation.get().getFacture() == null) {
                this.reservationService.deleteById(reservation.get().getId());
            } else {
                redirectAttributes.addFlashAttribute("error", "Impossible de supprimer une " + "réservation déjà payée.");
                return "redirect:/reservations";
            }
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Une erreur est survenue lors de " + "la suppression de la réservation");
            return "redirect:/reservations";
        }
        redirectAttributes.addFlashAttribute("success", "Votre réservation a été supprimée avec succès");
        return "redirect:/reservations";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute Reservation reservation, Model model, Principal principal, RedirectAttributes redirectAttributes) {

        Utilisateur utilisateur = this.utilisateurService.findByEmail(principal.getName());

        Vehicule vehicule = this.vehiculeService.findById(reservation.getVehicule().getId()).orElseThrow();
        Long vehiculeId = reservation.getVehicule().getId();

        if (!vehicule.isDisponible()) {
            redirectAttributes.addFlashAttribute("error", "Impossible de louer un véhicule indisponible.");
            return "redirect:/vehicules/" + vehicule.getId();
        }

        try {
            if (!utilisateur.isVerified() || !utilisateur.isVerifiedByAdmin()) {
                redirectAttributes.addFlashAttribute("error", "Votre compte doit être vérifié pour effectuer cette action.");
                return "redirect:/vehicules/" + vehiculeId;
            }
            List<Tarif> tarifs = this.vehiculeService.getTarifs(vehicule);

            LocalDate debut = reservation.getDateDebutReservation().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate fin = reservation.getDateFinReservation().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

            long nombreDeJours = ChronoUnit.DAYS.between(debut, fin);
            reservation.setNbJourReserve((int) nombreDeJours);

            float prix = calculerPrix(tarifs, reservation.getNbJourReserve());
            if (prix == 0) {
                redirectAttributes.addFlashAttribute("error", "Vous ne pouvez pas louer un véhicule au delà de 31 jours");
                return "redirect:/vehicules/" + vehiculeId;
            }
            reservation.setPrix(prix);
            reservation.setUtilisateur(utilisateur);
            reservation.setVehicule(vehicule);
            reservation.setFacture(null);

            this.reservationService.save(reservation);

            return "redirect:/reservations";

        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Une erreur est survenue lors de " + "la création de la réservation: " + e.getMessage());
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
        if (nbJours > 31) {
            return prixHebdo = 0;
        }
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
