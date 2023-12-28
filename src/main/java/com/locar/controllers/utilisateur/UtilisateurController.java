package com.locar.controllers.utilisateur;

import com.locar.entities.Utilisateur;
import com.locar.services.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/users")
public class UtilisateurController {

    @Autowired
    private UtilisateurService utilisateurService;

    @GetMapping
    public String getAllUsers(Model model) {
        model.addAttribute("users", utilisateurService.findAll());
        return "users/list";
    }

    @GetMapping("/{id}")
    public String getUserById(@PathVariable Long id, Model model) {
        model.addAttribute("user", utilisateurService.findById(id).orElse(null));
        return "users/detail";
    }

    @GetMapping("/new")
    public String newUser(Model model) {
        model.addAttribute("user", new Utilisateur());
        return "users/new";
    }

    @PostMapping
    public String createUser(@ModelAttribute Utilisateur user) {
        utilisateurService.save(user);
        return "redirect:/users";
    }

    @GetMapping("/edit/{id}")
    public String editUser(@PathVariable Long id, Model model) {
        model.addAttribute("user", utilisateurService.findById(id).orElse(null));
        return "users/edit";
    }

    @PostMapping("/{id}")
    public String updateUser(@PathVariable Long id, @ModelAttribute Utilisateur userDetails) {
        Utilisateur user = utilisateurService.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id " + id));
        user.setNom(userDetails.getNom());
        user.setEmail(userDetails.getEmail());
        utilisateurService.save(user);
        return "redirect:/users";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        utilisateurService.deleteById(id);
        return "redirect:/users";
    }
}
