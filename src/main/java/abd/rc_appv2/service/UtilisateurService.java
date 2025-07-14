package abd.rc_appv2.service;

import abd.rc_appv2.model.Utilisateur;
import abd.rc_appv2.repository.UtilisateurRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UtilisateurService {

    private final UtilisateurRepository utilisateurRepository;

    public UtilisateurService(UtilisateurRepository utilisateurRepository) {
        this.utilisateurRepository = utilisateurRepository;
    }

    public Utilisateur saveUtilisateur(Utilisateur utilisateur) {
        return utilisateurRepository.save(utilisateur);
    }

    public Utilisateur getByEmail(String email) {
        return utilisateurRepository.findByEmail(email).orElse(null);
    }

    public List<Utilisateur> getAll() {
        return utilisateurRepository.findAll();
    }
}
