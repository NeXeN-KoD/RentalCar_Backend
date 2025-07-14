package abd.rc_appv2.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.DATE)
    private Date dateDebut;

    @Temporal(TemporalType.DATE)
    private Date dateFin;

    private String statut; // CONFIRMEE, ANNULEE, TERMINEE
    private double totalPrix;

    @ManyToOne
    private Utilisateur utilisateur;

    @ManyToOne
    private Vehicule vehicule;
}
