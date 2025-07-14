package abd.rc_appv2.repository;

import abd.rc_appv2.model.Reservation;
import abd.rc_appv2.model.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByUtilisateur(Utilisateur utilisateur);
}
