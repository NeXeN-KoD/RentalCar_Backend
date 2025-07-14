package abd.rc_appv2.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Paiement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double montant;
    private String type; // CB, PayPal, Espèces
    private String statut; // PAYÉ, EN_ATTENTE

    @Temporal(TemporalType.DATE)
    private Date datePaiement;

    @OneToOne
    private Reservation reservation;
}
