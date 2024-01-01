package com.locar.services;

import com.locar.dao.UtilisateurRepository;
import com.locar.entities.Utilisateur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Service
public class UtilisateurService implements UserDetailsService {

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    public List<Utilisateur> findAll() {
        return utilisateurRepository.findAll();
    }

    public Optional<Utilisateur> findById(Long id) {
        return utilisateurRepository.findById(id);
    }

    public Utilisateur findByEmail(String email) {
        return utilisateurRepository.findByEmail(email);
    }

    public Utilisateur save(Utilisateur user) {
        return utilisateurRepository.save(user);
    }

    public void deleteById(Long id) {
        utilisateurRepository.deleteById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Utilisateur user = utilisateurRepository.findByEmail(username);

        return new User(user.getUsername(), user.getPassword(), getGrantedAuthorities(user.getRoles()));
    }

    private List<GrantedAuthority> getGrantedAuthorities(String roles) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String role : roles.split(",")) {
            authorities.add(new SimpleGrantedAuthority(role.trim()));
        }
        return authorities;
    }

}
