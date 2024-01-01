package com.locar.controllers.pdf;

import com.locar.entities.*;
import com.locar.exceptions.UnauthorizedAccessException;
import com.locar.services.PdfService;
import com.locar.services.ReservationService;
import com.locar.services.TarifService;
import com.locar.services.UtilisateurService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.context.Context;

import java.security.Principal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Controller
@RequestMapping("/facture/pdf")
public class FactureController {

    @Autowired
    private final PdfService pdfService;

    @Autowired
    private final ReservationService reservationService;

    @Autowired
    private final TarifService tarifService;

    @Autowired
    private final UtilisateurService utilisateurService;

    public FactureController(PdfService pdfService, ReservationService reservationService,
                             TarifService tarifService, UtilisateurService utilisateurService) {
        this.pdfService = pdfService;
        this.reservationService = reservationService;
        this.tarifService = tarifService;
        this.utilisateurService = utilisateurService;
    }

    @GetMapping("/{id}")
    public @ResponseBody void getFactureAsPdf(@PathVariable Long id, HttpServletResponse response, Principal principal) {
        Optional<Reservation> reservationOpt = reservationService.findById(id);
        Utilisateur utilisateur = this.utilisateurService.findByEmail(principal.getName());
        byte[] bytes = null;

        if (!reservationOpt.isPresent()) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        Reservation reservation = reservationOpt.get();
        Facture facture = reservation.getFacture();

        if (!Objects.equals(reservation.getUtilisateur().getId(), utilisateur.getId())) {
            throw new UnauthorizedAccessException("L'utilisateur n'est pas autorisé à accéder à cette facture.");
        }

        Vehicule vehicule = reservation.getVehicule();
        List<Tarif> tarifs = tarifService.findByVehicule(vehicule);

        Tarif tarif = new Tarif();
        for (Tarif tarifLoop : tarifs) {
            if (Objects.equals(tarifLoop.getCalendrier(), "Jour")) {
                tarif = tarifLoop;
                break;
            }
        }

        Context context = new Context();
        context.setVariable("reservation", reservation);
        context.setVariable("vehicule", vehicule);
        context.setVariable("tarif", tarif);

        if (facture != null) {
            context.setVariable("facture", facture);
            bytes = pdfService.generatePdf("pdf/facture/home", context);
        } else {
            bytes = pdfService.generatePdf("pdf/devis/home", context);
        }

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "inline; filename=facture_" + reservation.getId() + ".pdf");

        try {
            response.getOutputStream().write(bytes);
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

}
