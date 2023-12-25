package com.locar.services;

import com.locar.dao.UtilisateurRepository;
import com.locar.entities.Utilisateur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UtilisateurService {

    @Autowired
    private UtilisateurRepository userRepository;

    public List<Utilisateur> findAll() {
        return userRepository.findAll();
    }

    public Optional<Utilisateur> findById(Long id) {
        return userRepository.findById(Math.toIntExact(id));
    }

    public Utilisateur save(Utilisateur user) {
        return userRepository.save(user);
    }

    public void deleteById(Long id) {
        userRepository.deleteById(Math.toIntExact(id));
    }
}
