package com.locar.controllers.auth;

import com.locar.entities.Utilisateur;
import com.locar.services.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import java.util.Random;

@Controller
@RequestMapping("/register")
public class RegisterController {

    Random random =new Random();
    @Autowired
    private UtilisateurService utilisateurService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public RegisterController(UtilisateurService utilisateurService, BCryptPasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
        this.utilisateurService = utilisateurService;
    }
    @GetMapping
    public String showSignupForm(Model model) {
        model.addAttribute("user", new Utilisateur());
        return "auth/register";
    }



    @PostMapping
    public String processSignup(Utilisateur utilisateur,
                                @RequestParam("permis") MultipartFile permisFile,
                                RedirectAttributes redirectAttributes) {
        // Check if the uploaded file is not empty
        if (permisFile.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Veuillez remplir le champs de permis de conduire.");
            return "redirect:/register";
        }

        // Validate the file type (make sure it's either jpg or png)
        String mimeType = permisFile.getContentType();
        String originalFileName = permisFile.getOriginalFilename().toLowerCase();
        boolean isImage = mimeType != null && (mimeType.equals("image/jpeg") || mimeType.equals("image/png"));
        boolean isValidExtension = originalFileName.endsWith(".jpg") || originalFileName.endsWith(".jpeg") || originalFileName.endsWith(".png");

        if (!isImage || !isValidExtension) {
            redirectAttributes.addFlashAttribute("error", "L'extension de votre permis de conduire n'est pas valide.");
            return "redirect:/register";
        }

        // Encrypt the user's password
        utilisateur.setPassword(passwordEncoder.encode(utilisateur.getPassword()));

        // Save the user to generate the ID
        utilisateur.setPermisPath(originalFileName);
        utilisateur = utilisateurService.save(utilisateur);

        // Construct the file path using the user ID
        String directoryPath = "src\\main\\resources\\static\\uploads\\permis\\users\\" + utilisateur.getId();
        Path directory = Paths.get(directoryPath);
        System.out.println(directoryPath);

        try {
            if (!Files.exists(directory)) {
                Files.createDirectories(directory); // Create directory if it doesn't exist
            }

            String fileName = StringUtils.cleanPath(Objects.requireNonNull(permisFile.getOriginalFilename()));
            Path filePath = directory.resolve(fileName);
            Files.copy(permisFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            // Set the file path in the 'permisPath' attribute of the 'Utilisateur' entity
            utilisateur.setPermisPath(filePath.toString());

            // Update the user entity to save the file path in the database
            utilisateurService.save(utilisateur);
        } catch (IOException e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Une erreur est survenue lors de l'enregistrement du fichier.");
            return "redirect:/register"; // Ensure this is the correct redirect on error
        }

        // Redirect to the login page after successful registration
        return "redirect:/login"; // Or redirect to an appropriate success page
    }

}