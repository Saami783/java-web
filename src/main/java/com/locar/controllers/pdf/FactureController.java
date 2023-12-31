package com.locar.controllers.pdf;

import com.locar.entities.Facture;
import com.locar.entities.Reservation;
import com.locar.entities.Vehicule;
import com.locar.entities.Tarif;
import com.locar.services.FactureService;
import com.locar.services.PdfService;
import com.locar.services.ReservationService;
import com.locar.services.VehiculeService;
import com.locar.services.TarifService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.context.Context;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Optional;
import java.util.List;

@Controller
@RequestMapping("/facture/pdf")
public class FactureController {

    @Autowired
    private PdfService pdfService;

    @Autowired
    private FactureService factureService;

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private VehiculeService vehiculeService;

    @Autowired
    private TarifService tarifService;

    @GetMapping("/{id}")
    public @ResponseBody void getFactureAsPdf(@PathVariable Long id, HttpServletResponse response) {
        Optional<Facture> factureOpt = factureService.findById(id);

        if (!factureOpt.isPresent()) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        Facture facture = factureOpt.get();
        Reservation reservation = facture.getReservation();
        Vehicule vehicule = reservation.getVehicule();
        List<Tarif> tarifs = tarifService.findByVehicule(vehicule);

        Context context = new Context();
        context.setVariable("facture", facture);
        context.setVariable("reservation", reservation);
        context.setVariable("vehicule", vehicule);
        context.setVariable("tarifs", tarifs);

        byte[] bytes = pdfService.generatePdf("factureTemplate", context);

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "inline; filename=facture_" + id + ".pdf");

        try {
            response.getOutputStream().write(bytes);
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
