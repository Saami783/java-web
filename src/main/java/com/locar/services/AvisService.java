package com.locar.services;

import com.locar.dao.AvisRepository;
import com.locar.entities.Avis;
import com.locar.entities.Reservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AvisService {

    @Autowired
    private AvisRepository avisRepository;

    public Avis save(Avis avis) {
        return avisRepository.save(avis);
    }

    public Optional<Avis> findById(long id) {
        return avisRepository.findById(id);
    }

    public List<Avis> findAvisByVehiculeId(Long vehiculeId) {
        return avisRepository.findAvisByVehiculeId(vehiculeId);
    }

}
