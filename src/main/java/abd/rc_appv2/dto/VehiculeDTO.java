package abd.rc_appv2.dto;

import abd.rc_appv2.model.Vehicule;

public class VehiculeDTO {
    private Long id;
    private String marque;
    private String modele;
    private String immatriculation;
    private Double prixParJour;
    private String statut;
    private Long categorieId;
    private String categorieNom;

    // Constructeur à partir de l'entité
    public VehiculeDTO(Vehicule vehicule) {
        this.id = vehicule.getId();
        this.marque = vehicule.getMarque();
        this.modele = vehicule.getModele();
        this.immatriculation = vehicule.getImmatriculation();
        this.prixParJour = vehicule.getPrixParJour();
        this.statut = vehicule.getStatut();
        this.categorieId = vehicule.getCategorie().getId();
        this.categorieNom = vehicule.getCategorie().getNom();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMarque() {
        return marque;
    }

    public void setMarque(String marque) {
        this.marque = marque;
    }

    public String getModele() {
        return modele;
    }

    public void setModele(String modele) {
        this.modele = modele;
    }

    public String getImmatriculation() {
        return immatriculation;
    }

    public void setImmatriculation(String immatriculation) {
        this.immatriculation = immatriculation;
    }

    public Double getPrixParJour() {
        return prixParJour;
    }

    public void setPrixParJour(Double prixParJour) {
        this.prixParJour = prixParJour;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public Long getCategorieId() {
        return categorieId;
    }

    public void setCategorieId(Long categorieId) {
        this.categorieId = categorieId;
    }

    public String getCategorieNom() {
        return categorieNom;
    }

    public void setCategorieNom(String categorieNom) {
        this.categorieNom = categorieNom;
    }

    // getters et setters
}
