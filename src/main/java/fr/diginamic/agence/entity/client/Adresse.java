package fr.diginamic.agence.entity.client;

import javax.persistence.*;

@Entity
public class Adresse {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    protected Long id;
    protected String ville;
    protected String codePostal;
    protected String numeroRue;
    protected String rue;
    protected String tel;
    protected String mail;

    @OneToOne @JoinColumn (name="client")
    protected Client client;

    public Adresse() {}

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getRue() {
        return rue;
    }

    public void setRue(String rue) {
        this.rue = rue;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public String getCodePostal() {
        return codePostal;
    }

    public void setCodePostal(String codePostal) {
        this.codePostal = codePostal;
    }

    public String getNumeroRue() {
        return numeroRue;
    }

    public void setNumeroRue(String numeroRue) {
        this.numeroRue = numeroRue;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }
}
