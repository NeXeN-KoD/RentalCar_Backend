package abd.rc_appv2.controller;

import abd.rc_appv2.model.Categorie;
import abd.rc_appv2.repository.CategorieRepository;
import abd.rc_appv2.service.CategorieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@CrossOrigin(origins = "http://localhost:3000")
public class CategorieController {

    @Autowired
    private CategorieRepository categorieRepository;

    @GetMapping
    public List<Categorie> getAll() {
        return categorieRepository.findAll();
    }

    @PostMapping
    public Categorie create(@RequestBody Categorie categorie) {
        return categorieRepository.save(categorie);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Categorie> update(@PathVariable Long id, @RequestBody Categorie updated) {
        return categorieRepository.findById(id)
                .map(categorie -> {
                    categorie.setNom(updated.getNom());
                    return ResponseEntity.ok(categorieRepository.save(categorie));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!categorieRepository.existsById(id)) return ResponseEntity.notFound().build();
        categorieRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
