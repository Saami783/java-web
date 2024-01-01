package com.locar.services;

import com.locar.dao.TarifRepository;
import com.locar.entities.Tarif;
import com.locar.entities.Vehicule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TarifService {
    @Autowired
    private TarifRepository tarifRepository;

    public List<Tarif> findAll() {
        return tarifRepository.findAll();
    }

    public Optional<Tarif> findById(Long id) {
        return tarifRepository.findById(id);
    }

    public Tarif save(Tarif tarif) {
        return tarifRepository.save(tarif);
    }

    public void deleteById(Long id) { tarifRepository.deleteById(id); }

    public List<Tarif> findByVehicule(Vehicule vehicule) {
        return tarifRepository.findByVehicule(vehicule);
    }
}
