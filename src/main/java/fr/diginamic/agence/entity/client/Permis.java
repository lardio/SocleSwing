package fr.diginamic.agence.entity.client;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Permis {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    protected Long id;
    protected Long numero;
    protected LocalDate obtention;

    @OneToOne
    @JoinColumn (name = "id_client")
    protected Client client;

    @ManyToOne
    @JoinColumn (name = "id_typePermis", nullable = false)
    protected TypePermis typePermis;

    public Permis () {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getNumero() {
        return numero;
    }

    public void setNumero(Long numero) {
        this.numero = numero;
    }

    public LocalDate getObtention() {
        return obtention;
    }

    public void setObtention(LocalDate obtention) {
        this.obtention = obtention;
    }

    public TypePermis getTypePermis() {
        return typePermis;
    }

    public void setTypePermis(TypePermis typePermis) {
        this.typePermis = typePermis;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
