package abd.rc_appv2.controller;

import abd.rc_appv2.model.Utilisateur;
import abd.rc_appv2.repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;


@RestController
@RequestMapping("/api/utilisateurs")
public class UtilisateurController {

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @GetMapping
    public List<Utilisateur> getAll() {
        return utilisateurRepository.findAll();
    }

    @PostMapping
    public Utilisateur create(@RequestBody Utilisateur u) {
        return utilisateurRepository.save(u);
    }

    @PutMapping("/{id}")
    public Utilisateur update(@PathVariable Long id, @RequestBody Utilisateur u) {
        u.setId(id);
        return utilisateurRepository.save(u);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        utilisateurRepository.deleteById(id);
    }

    @PutMapping("/{id}/modifier-mdp")
    public ResponseEntity<String> modifierMotDePasse(@PathVariable Long id, @RequestBody Map<String, String> payload) {
        String ancienMotDePasse = payload.get("ancienMotDePasse");
        String nouveauMotDePasse = payload.get("nouveauMotDePasse");

        Optional<Utilisateur> opt = utilisateurRepository.findById(id);
        if (opt.isEmpty()) return ResponseEntity.notFound().build();

        Utilisateur utilisateur = opt.get();

        // Comparaison directe sans encoder
        if (!utilisateur.getMotDePasse().equals(ancienMotDePasse)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Ancien mot de passe incorrect");
        }

        // Enregistrement du nouveau mot de passe tel quel (non chiffré)
        utilisateur.setMotDePasse(nouveauMotDePasse);
        utilisateurRepository.save(utilisateur);

        return ResponseEntity.ok("Mot de passe mis à jour avec succès");
    }

}
