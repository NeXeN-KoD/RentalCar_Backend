package abd.rc_appv2.controller;

import abd.rc_appv2.model.Reservation;
import abd.rc_appv2.model.Utilisateur;
import abd.rc_appv2.model.Vehicule;
import abd.rc_appv2.repository.ReservationRepository;
import abd.rc_appv2.repository.UtilisateurRepository;
import abd.rc_appv2.repository.VehiculeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private VehiculeRepository vehiculeRepository;

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @PostMapping("/reserver")
    public Reservation reserverVehicule(
            @RequestParam Long utilisateurId,
            @RequestParam Long vehiculeId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date dateDebut,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date dateFin) {

        Utilisateur utilisateur = utilisateurRepository.findById(utilisateurId).orElseThrow();
        Vehicule vehicule = vehiculeRepository.findById(vehiculeId).orElseThrow();

        if (!vehicule.getStatut().equals("DISPONIBLE")) {
            throw new RuntimeException("Véhicule non disponible !");
        }

        long diff = (dateFin.getTime() - dateDebut.getTime()) / (1000 * 60 * 60 * 24);
        double totalPrix = diff * vehicule.getPrixParJour();

        Reservation reservation = new Reservation();
        reservation.setDateDebut(dateDebut);
        reservation.setDateFin(dateFin);
        reservation.setStatut("EN_ATTENTE");
        reservation.setTotalPrix(totalPrix);
        reservation.setUtilisateur(utilisateur);
        reservation.setVehicule(vehicule);

        vehicule.setStatut("EN_LOCATION");
        vehiculeRepository.save(vehicule);

        return reservationRepository.save(reservation);
    }

    @GetMapping("/utilisateur/{utilisateurId}")
    public List<Reservation> getReservationsByUtilisateur(@PathVariable Long utilisateurId) {
        return reservationRepository.findByUtilisateurId(utilisateurId);
    }

    // ✅ Get All
    @GetMapping
    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    // ✅ Get One
    @GetMapping("/{id}")
    public ResponseEntity<Reservation> getReservationById(@PathVariable Long id) {
        return reservationRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ✅ Update
    @PutMapping("/{id}")
    public ResponseEntity<Reservation> updateReservation(
            @PathVariable Long id,
            @RequestBody Reservation updated) {
        return reservationRepository.findById(id).map(existing -> {
            existing.setDateDebut(updated.getDateDebut());
            existing.setDateFin(updated.getDateFin());
            existing.setStatut(updated.getStatut());
            existing.setTotalPrix(updated.getTotalPrix());
            return ResponseEntity.ok(reservationRepository.save(existing));
        }).orElse(ResponseEntity.notFound().build());
    }

    // ✅ Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteReservation(@PathVariable Long id) {
        return reservationRepository.findById(id).map(reservation -> {
            Vehicule vehicule = reservation.getVehicule();
            // 🟢 Remettre le véhicule à "DISPONIBLE"
            if (vehicule != null) {
                vehicule.setStatut("DISPONIBLE");
                vehiculeRepository.save(vehicule);
            }
            reservationRepository.delete(reservation);
            return ResponseEntity.ok().build();
        }).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/confirmer")
    public ResponseEntity<Reservation> confirmerReservation(@PathVariable Long id) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Réservation non trouvée"));

        reservation.setStatut("CONFIRMEE");

        // mettre aussi le véhicule en EN_LOCATION
        Vehicule vehicule = reservation.getVehicule();
        vehicule.setStatut("EN_LOCATION");
        vehiculeRepository.save(vehicule);

        reservationRepository.save(reservation);
        return ResponseEntity.ok(reservation);
    }

}
