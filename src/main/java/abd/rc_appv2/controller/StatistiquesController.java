package abd.rc_appv2.controller;


import abd.rc_appv2.repository.CategorieRepository;
import abd.rc_appv2.repository.UtilisateurRepository;
import abd.rc_appv2.repository.VehiculeRepository;
import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/stats")
public class StatistiquesController {

    @Autowired
    private VehiculeRepository vehiculeRepo;

    @Autowired
    private CategorieRepository categorieRepo;

    @Autowired
    private UtilisateurRepository utilisateurRepo;

    @GetMapping
    public Map<String, Long> getStats() {
        Map<String, Long> stats = new HashMap<>();
        stats.put("vehicules", vehiculeRepo.count());
        stats.put("categories", categorieRepo.count());
        stats.put("utilisateurs", utilisateurRepo.count());
        return stats;
    }
}
