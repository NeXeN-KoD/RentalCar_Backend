package abd.rc_appv2.controller;

import abd.rc_appv2.dto.LoginRequest;
import abd.rc_appv2.dto.RegisterRequest;
import abd.rc_appv2.model.Utilisateur;
import abd.rc_appv2.model.Utilisateur.Role;
import abd.rc_appv2.service.UtilisateurService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UtilisateurService utilisateurService;

    public AuthController(UtilisateurService utilisateurService) {
        this.utilisateurService = utilisateurService;
    }

    @PostMapping("/register")
    public Utilisateur register(@RequestBody RegisterRequest request) {
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setNom(request.getNom());
        utilisateur.setPrenom(request.getPrenom());
        utilisateur.setEmail(request.getEmail());
        utilisateur.setMotDePasse(request.getMotDePasse()); // 🔐 à crypter plus tard
        utilisateur.setRole(Role.CLIENT); // affecter automatiquement "CLIENT"
        return utilisateurService.saveUtilisateur(utilisateur);
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest request) {
        Utilisateur utilisateur = utilisateurService.getByEmail(request.getEmail());

        if (utilisateur != null && utilisateur.getMotDePasse().equals(request.getMotDePasse())) {
            return "Bienvenue " + utilisateur.getRole() + " : " + utilisateur.getEmail();
        } else {
            return "Email ou mot de passe incorrect.";
        }
    }
}
