package com.locar.services;

import com.locar.dao.FactureRepository;
import com.locar.entities.Facture;
import com.locar.entities.Vehicule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FactureService {

    @Autowired
    private FactureRepository factureRepository;

    public List<Facture> findAll() {
        return factureRepository.findAll();
    }

    public Optional<Facture> findById(Long id) {
        return factureRepository.findById(id);
    }

    public Facture save(Facture facture) {
        return factureRepository.save(user);
    }

    public void deleteById(Long id) {
        factureRepository.deleteById(id);
    }
}
