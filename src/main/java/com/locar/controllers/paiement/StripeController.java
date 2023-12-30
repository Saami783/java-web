package com.locar.controllers.paiement;

import com.locar.config.StripeProperties;
import com.locar.entities.Reservation;
import com.locar.entities.Utilisateur;
import com.locar.services.ReservationService;
import com.locar.services.UtilisateurService;
import com.locar.services.VehiculeService;
import com.stripe.Stripe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.stripe.param.checkout.SessionCreateParams;
import java.security.Principal;
import java.util.Optional;
import java.util.UUID;

import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/paiement/stripe")
public class StripeController {
    private final StripeProperties stripeProperties;
    private final ReservationService reservationService;
    private final UtilisateurService utilisateurService;
    private final VehiculeService vehiculeService;

    @Autowired
    public StripeController(StripeProperties stripeProperties, ReservationService reservationService,
                            UtilisateurService utilisateurService, VehiculeService vehiculeService) {
        this.stripeProperties = stripeProperties;
        this.reservationService = reservationService;
        this.utilisateurService = utilisateurService;
        this.vehiculeService = vehiculeService;
        Stripe.apiKey = stripeProperties.getApiKey();
    }

    @GetMapping("/{id}")
    public String createCheckoutSession(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            Optional<Reservation> optionalReservation = reservationService.findById(id);
            if (!optionalReservation.isPresent()) {
                redirectAttributes.addFlashAttribute("error", "Réservation non trouvée.");
                return "redirect:/reservations";
            }
            Reservation reservation = optionalReservation.get();

            String paymentToken = UUID.randomUUID().toString();
            reservation.setPaymentToken(paymentToken);
            reservationService.save(reservation);

            long totalAmount = convertToCents(reservation.getPrix());

            SessionCreateParams params = SessionCreateParams.builder()
                    .setSuccessUrl("http://localhost:8081/paiement/stripe/success?session_id={CHECKOUT_SESSION_ID}")
                    .setCancelUrl("http://localhost:8081/paiement/stripe/cancel")
                    .addLineItem(
                            SessionCreateParams.LineItem.builder()
                                    .setQuantity(1L)
                                    .setPriceData(
                                            SessionCreateParams.LineItem.PriceData.builder()
                                                    .setCurrency("eur")
                                                    .setUnitAmount(totalAmount)
                                                    .setProductData(
                                                            SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                                    .setName("Location " + reservation.getVehicule().getModele() + " " + reservation.getVehicule().getCategorie().getLibelle())
                                                                    .build())
                                                    .build())
                                    .build())
                    .putMetadata("payment_token", paymentToken)
                    .setMode(SessionCreateParams.Mode.PAYMENT)
                    .build();

            Session session = Session.create(params);

            return "redirect:" + session.getUrl();
        } catch (StripeException e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Erreur lors de la création de la session de paiement Stripe.");
            return "redirect:/erreur";
        }
    }

    @GetMapping("/success")
    public String paymentSuccess(@RequestParam String session_id, RedirectAttributes redirectAttributes) {
        try {
            Stripe.apiKey = stripeProperties.getApiKey();

            Session session = Session.retrieve(session_id);
            String paymentToken = session.getMetadata().get("payment_token");

            Optional<Reservation> reservationOpt = reservationService.findByPaymentToken(paymentToken);
            if (!reservationOpt.isPresent()) {
                redirectAttributes.addFlashAttribute("error", "Réservation non trouvée avec le token de paiement.");
                return "redirect:/erreur";
            }

            Reservation reservation = reservationOpt.get();

            System.out.println(reservation);
            System.exit(0);

            redirectAttributes.addFlashAttribute("success", "Paiement réussi!");
            return "redirect:/reservations";
        } catch (StripeException e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Erreur lors de la récupération des informations de paiement.");
            return "redirect:/erreur";
        }
    }

    @GetMapping("/cancel")
    public String paymentCancelled(RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("error", "Paiement annulé.");
        return "redirect:/reservations";
    }

    private long convertToCents(double amount) {
        return (long) (amount * 100);
    }
}
