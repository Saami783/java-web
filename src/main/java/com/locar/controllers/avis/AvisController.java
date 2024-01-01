package com.locar.controllers.avis;

import com.locar.entities.Avis;
import com.locar.entities.Reservation;
import com.locar.entities.Utilisateur;
import com.locar.entities.Vehicule;
import com.locar.services.AvisService;
import com.locar.services.ReservationService;
import com.locar.services.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.security.Principal;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/avis")
public class AvisController {

    @Autowired
    private AvisService avisService;

    @Autowired
    private ReservationService reservationService;

    private UtilisateurService utilisateurService;

    public AvisController(ReservationService reservationService, UtilisateurService utilisateurService, AvisService avisService) {
        this.utilisateurService = utilisateurService;
        this.reservationService = reservationService;
        this.avisService = avisService;
    }

    @PostMapping("/create")
    public String createAvis(@ModelAttribute Vehicule vehicule, @ModelAttribute("avis") Avis avis, Principal principal, RedirectAttributes redirectAttributes) {

            Utilisateur utilisateur = this.utilisateurService.findByEmail(principal.getName());
            List<Reservation> reservations = this.reservationService.findByUtilisateurId(utilisateur.getId());

            Reservation currentReservation = null;
            for(Reservation reservation: reservations){
                if(reservation.getVehicule().getId() == vehicule.getId()
                        && Objects.equals(reservation.getUtilisateur().getId(), utilisateur.getId())
                        && reservation.getFacture() != null){
                    currentReservation = reservation;
                    break;
                }
            }
            if(currentReservation != null) {
                avis.setReservation(currentReservation);
                avisService.save(avis);
                redirectAttributes.addFlashAttribute("success", "Votre avis a été publié.");
                return "redirect:/vehicules/"+vehicule.getId();
            } else {
                redirectAttributes.addFlashAttribute("error", "Vous ne pouvez pas publier d'avis " +
                        "pour ce vehicule.");
                return "redirect:/vehicules/"+vehicule.getId();
            }
    }

}
