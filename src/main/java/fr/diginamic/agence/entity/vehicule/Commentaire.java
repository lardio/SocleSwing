package fr.diginamic.agence.entity.vehicule;

import javax.persistence.*;

@Entity
public class Commentaire {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    protected Long id;

    @ManyToOne
    @JoinColumn (name = "id_vehicule", nullable = false)
    protected Vehicule vehicule;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Vehicule getVehicule() {
        return vehicule;
    }

    public void setVehicule(Vehicule vehicule) {
        this.vehicule = vehicule;
    }
}
