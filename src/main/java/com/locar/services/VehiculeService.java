package com.locar.services;

import com.locar.dao.VehiculeRepository;
import com.locar.entities.Tarif;
import com.locar.entities.Vehicule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VehiculeService {

    @Autowired
    private VehiculeRepository vehiculeRepository;

    public List<Vehicule> findAll() {
        return vehiculeRepository.findAll();
    }

    public Optional<Vehicule> findById(Long id) {
        return vehiculeRepository.findById(id);
    }

    public Vehicule save(Vehicule vehicule) {
        return vehiculeRepository.save(vehicule);
    }

    public void deleteById(Long id) {
        vehiculeRepository.deleteById(id);
    }


    public List<Tarif> getTarifs(Vehicule vehicule) { return vehicule.getTarifs(); }

}
