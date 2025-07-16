package abd.rc_appv2.controller;

import abd.rc_appv2.model.Paiement;
import abd.rc_appv2.model.Reservation;
import abd.rc_appv2.repository.PaiementRepository;
import abd.rc_appv2.repository.ReservationRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/paiement")
public class PaiementController {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private PaiementRepository paiementRepository;

    @PostMapping
    public ResponseEntity<Paiement> effectuerPaiement(@RequestBody Map<String, Object> payload) {
        Long reservationId = Long.parseLong(payload.get("reservationId").toString());
        String type = payload.get("type").toString();

        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new RuntimeException("Réservation introuvable"));

        Paiement paiement = new Paiement();
        paiement.setReservation(reservation);
        paiement.setType(type);
        paiement.setStatut("EN_COURS");
        paiement.setMontant(reservation.getTotalPrix());
        paiement.setDatePaiement(new Date());

        paiementRepository.save(paiement);

        // mettre à jour le statut de la réservation
        reservation.setStatut("RESERVÉ");
        reservationRepository.save(reservation);

        return ResponseEntity.ok(paiement);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteReservation(@PathVariable Long id) {
        Optional<Reservation> optionalReservation = reservationRepository.findById(id);

        if (optionalReservation.isPresent()) {
            Reservation reservation = optionalReservation.get();

            // 🔁 Supprimer d'abord le paiement lié
            paiementRepository.deleteByReservationId(reservation.getId());

            reservationRepository.delete(reservation);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}/confirmer")
    public ResponseEntity<?> confirmerPaiement(@PathVariable Long id) {
        Optional<Paiement> paiementOpt = paiementRepository.findById(id);
        if (paiementOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Paiement introuvable");
        }

        Paiement paiement = paiementOpt.get();
        paiement.setStatut("CONFIRMEE");
        paiementRepository.save(paiement);

        return ResponseEntity.ok("Paiement confirmé");
    }


    @GetMapping("/en-attente")
    public List<Paiement> getPaiementsEnAttente() {
        return paiementRepository.findByStatut("EN_ATTENTE");
    }

    @GetMapping("/all")
    public List<Paiement> getAllPaiements() {
        return paiementRepository.findAll();
    }

}
