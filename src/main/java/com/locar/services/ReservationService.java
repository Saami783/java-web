package com.locar.services;

import com.locar.dao.ReservationRepository;
import com.locar.entities.Reservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    public List<Reservation> findAll() {
        return reservationRepository.findAll();
    }

    public Optional<Reservation> findById(Long id) {
        return reservationRepository.findById(id);
    }

    public Reservation save(Reservation user) {
        return reservationRepository.save(user);
    }

    public void deleteById(Long id) {
        reservationRepository.deleteById(id);
    }

}
