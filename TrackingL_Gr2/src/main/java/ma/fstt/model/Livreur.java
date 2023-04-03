package ma.fstt.model;


// java bean
public class Livreur {
    private Long id_livreur ;

    private String nom ;

    private String telephone ;
    private String status;

    public Livreur() {
    }

    public Livreur(Long id_livreur, String nom, String telephone, String status) {
        this.id_livreur = id_livreur;
        this.nom = nom;
        this.telephone = telephone;
        this.status = status;
    }

    public Long getId_livreur() {
        return id_livreur;
    }

    public void setId_livreur(Long id_livreur) {
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Livreur{" +
                "id_livreur=" + id_livreur +
                ", nom='" + nom + '\'' +
                ", telephone='" + telephone + '\'' +
                '}';
    }
}
