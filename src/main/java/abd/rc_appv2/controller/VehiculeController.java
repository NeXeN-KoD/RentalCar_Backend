package abd.rc_appv2.controller;

import abd.rc_appv2.dto.VehiculeDTO;
import abd.rc_appv2.model.Categorie;
import abd.rc_appv2.model.Vehicule;
import abd.rc_appv2.repository.CategorieRepository;
import abd.rc_appv2.repository.VehiculeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/vehicules")
@CrossOrigin(origins = "http://localhost:3000")
public class VehiculeController {

    @Autowired
    private VehiculeRepository vehiculeRepository;

    @Autowired
    private CategorieRepository categorieRepository;

    @GetMapping
    public List<Vehicule> getAll() {
        return vehiculeRepository.findAll();
    }

    @PostMapping
    public Vehicule create(@RequestBody Vehicule vehicule) {
        if (vehicule.getCategorie() != null) {
            Categorie cat = categorieRepository.findById(vehicule.getCategorie().getId()).orElse(null);
            vehicule.setCategorie(cat);
        }
        return vehiculeRepository.save(vehicule);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Vehicule> update(@PathVariable Long id, @RequestBody Vehicule updated) {
        return vehiculeRepository.findById(id)
                .map(v -> {
                    v.setMarque(updated.getMarque());
                    v.setModele(updated.getModele());
                    v.setImmatriculation(updated.getImmatriculation());
                    v.setPrixParJour(updated.getPrixParJour());
                    v.setStatut(updated.getStatut());

                    if (updated.getCategorie() != null) {
                        Categorie cat = categorieRepository.findById(updated.getCategorie().getId()).orElse(null);
                        v.setCategorie(cat);
                    }

                    return ResponseEntity.ok(vehiculeRepository.save(v));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!vehiculeRepository.existsById(id)) return ResponseEntity.notFound().build();
        vehiculeRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/vehicules")
    public List<VehiculeDTO> getVehicules() {
        return vehiculeRepository.findAll().stream()
                .map(VehiculeDTO::new)
                .collect(Collectors.toList());
    }

}

