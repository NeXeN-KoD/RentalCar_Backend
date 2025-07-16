package abd.rc_appv2.repository;

import abd.rc_appv2.model.Paiement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface PaiementRepository extends JpaRepository<Paiement, Long> {
    @Transactional
    void deleteByReservationId(Long reservationId);
    List<Paiement> findByStatut(String statut);
}
