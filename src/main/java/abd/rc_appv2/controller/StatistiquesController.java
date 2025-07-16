package abd.rc_appv2.controller;

import abd.rc_appv2.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/stats")
public class StatistiquesController {

    @Autowired
    private VehiculeRepository vehiculeRepo;

    @Autowired
    private CategorieRepository categorieRepo;

    @Autowired
    private UtilisateurRepository utilisateurRepo;

    @Autowired
    private ReservationRepository reservationRepo;

    @Autowired
    private PaiementRepository paiementRepo;


    @GetMapping
    public Map<String, Object> getStats() {
        Map<String, Object> stats = new HashMap<>();

        stats.put("vehicules", vehiculeRepo.count());
        stats.put("categories", categorieRepo.count());
        stats.put("utilisateurs", utilisateurRepo.count());
        stats.put("reservations", reservationRepo.count());

        // Total des paiements (uniquement pour les réservations confirmées)
        double totalPaiementsConfirmes = paiementRepo.findAll().stream()
                .filter(p -> "CONFIRMEE".equalsIgnoreCase(p.getStatut()))
                .mapToDouble(p -> Optional.ofNullable(p.getMontant()).orElse(0.0))
                .sum();

        stats.put("paiements", totalPaiementsConfirmes);

        // Répartition des véhicules par statut (ex: DISPONIBLE, EN_LOCATION)
        Map<String, Long> vehiculeParStatut = vehiculeRepo.findAll().stream()
                .collect(Collectors.groupingBy(
                        v -> v.getStatut().toString(),
                        Collectors.counting()
                ));
        stats.put("vehiculeStatutCounts", vehiculeParStatut);

        return stats;
    }
}
