package com.locar.dao;

import com.locar.entities.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByUtilisateurId(Long utilisateurId);
    public Optional<Reservation> findByPaymentToken(String paymentToken);
}
