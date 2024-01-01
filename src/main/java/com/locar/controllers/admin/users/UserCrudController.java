package com.locar.controllers.admin.users;

import com.locar.entities.Utilisateur;
import com.locar.services.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin/users")
public class UserCrudController {

    @Autowired
    private final UtilisateurService utilisateurService;

    public UserCrudController(UtilisateurService utilisateurService) {
        this.utilisateurService = utilisateurService;
    }

    @GetMapping
    public String index(Model model) {
        List<Utilisateur> utilisateurs = utilisateurService.findAll();
        model.addAttribute("utilisateurs", utilisateurs);
        return "admin/users/index";
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes) {
        Optional<Utilisateur> utilisateur = utilisateurService.findById(id);
        if(utilisateur.isPresent()) {
            model.addAttribute("utilisateur", utilisateur.get());
            return "admin/users/detail";
        }else {
            redirectAttributes.addFlashAttribute("error", "Impossible de modifier cette réservation");
            return "redirect:/admin/users";
        }

    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, Model model) {
        Utilisateur utilisateur = utilisateurService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        model.addAttribute("utilisateur", utilisateur);
        return "admin/users/edit";
    }

    @PostMapping("/edit/{id}")
    public String update(@PathVariable Long id, @ModelAttribute("utilisateur") Utilisateur updatedUser, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "admin/users/edit";
        }

        Optional<Utilisateur> existingUserOpt = utilisateurService.findById(id);

        if (!existingUserOpt.isPresent()) {
            redirectAttributes.addFlashAttribute("error", "Utilisateur non trouvé.");
            return "redirect:/admin/users";
        }

        Utilisateur existingUser = existingUserOpt.get();

        existingUser.setNom(updatedUser.getNom());
        existingUser.setPrenom(updatedUser.getPrenom());
        existingUser.setEmail(updatedUser.getEmail());
        existingUser.setTelephone(updatedUser.getTelephone());
        existingUser.setVerified(updatedUser.isVerified());
        existingUser.setVerifiedByAdmin(updatedUser.isVerifiedByAdmin());

        utilisateurService.save(existingUser);

        redirectAttributes.addFlashAttribute("success", "Utilisateur mis à jour avec succès.");
        return "redirect:/admin/users";
    }


}
