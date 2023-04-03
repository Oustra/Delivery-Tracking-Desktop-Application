package ma.fstt.model;

public class Delivery {
    private long id_delivery;
    private String adresse;
    private String status;
    private long id_livreur;
    private String nom;
    private String telephone;

    public Delivery(long id_delivery, String adresse, String status, long id_livreur) {
        this.id_delivery = id_delivery;
        this.adresse = adresse;
        this.status = status;
        this.id_livreur = id_livreur;
    }

    public Delivery(long id_delivery, String adresse, String status, String nom, String telephone) {
        this.id_delivery = id_delivery;
        this.adresse = adresse;
        this.status = status;
        this.nom = nom;
        this.telephone=telephone;
    }

    public long getId_delivery() {
        return id_delivery;
    }

    public void setId_delivery(long id_delivery) {
        this.id_delivery = id_delivery;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getId_livreur() {
        return id_livreur;
    }

    public void setId_livreur(long id_livreur) {
        this.id_livreur = id_livreur;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
}
