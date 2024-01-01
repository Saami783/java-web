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
import java.util.UUID;

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

        String fileUploadError = validateAndUploadFile(permisFile, utilisateur);
        if (fileUploadError != null) {
            redirectAttributes.addFlashAttribute("error", fileUploadError);
            return "redirect:/register";
        }

        utilisateur.setPassword(passwordEncoder.encode(utilisateur.getPassword()));
        utilisateur = utilisateurService.save(utilisateur);

        return "redirect:/login";
    }

    private String validateAndUploadFile(MultipartFile file, Utilisateur utilisateur) {
        if (file.isEmpty()) {
            return "Veuillez remplir le champ de permis de conduire.";
        }

        String mimeType = file.getContentType();
        String originalFileName = file.getOriginalFilename();
        if (originalFileName == null || mimeType == null) {
            return "Impossible de lire le nom de fichier ou le type MIME.";
        }

        originalFileName = originalFileName.toLowerCase();
        boolean isImage = mimeType.equals("image/jpeg") || mimeType.equals("image/png");
        boolean isValidExtension = originalFileName.endsWith(".jpg") || originalFileName.endsWith(".jpeg") || originalFileName.endsWith(".png");

        if (!isImage || !isValidExtension) {
            return "L'extension de votre permis de conduire n'est pas valide.";
        }

        try {
            // Génération d'une chaîne aléatoire de 20 caractères maximum
            String randomDirName = UUID.randomUUID().toString().replace("-", "").substring(0, 20);

            String directoryPath = "src\\main\\resources\\static\\uploads\\permis\\users\\" + randomDirName;
            Path directory = Paths.get(directoryPath);
            if (!Files.exists(directory)) {
                Files.createDirectories(directory);
            }

            String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
            String uniqueFileName = UUID.randomUUID().toString() + "_" + fileName;
            Path filePath = directory.resolve(uniqueFileName);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            utilisateur.setPermisPath("src\\main\\resources\\static\\uplGIoads\\permis\\users\\" + randomDirName + "\\" + uniqueFileName);

        } catch (IOException e) {
            return "Une erreur est survenue lors de l'enregistrement du fichier.";
        }

        return null;
    }



}