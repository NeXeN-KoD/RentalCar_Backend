package abd.rc_appv2.repository;

import abd.rc_appv2.model.Vehicule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VehiculeRepository extends JpaRepository<Vehicule, Long> {
    List<Vehicule> findByStatut(String statut);
}
