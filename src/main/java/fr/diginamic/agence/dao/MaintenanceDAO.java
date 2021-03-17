package fr.diginamic.agence.dao;

import fr.diginamic.agence.entity.reservation.Reservation;
import fr.diginamic.agence.entity.vehicule.Maintenance;

import javax.persistence.TypedQuery;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MaintenanceDAO extends SourceDAO<Maintenance> {

    public MaintenanceDAO() {
        super(Maintenance.class);
        this.tableName = Maintenance.class.getSimpleName();
    }

    public List<Maintenance> findEnCours () {
        List<Maintenance> listeRetour = new ArrayList<>();
        String request = "SELECT t from " +this.tableName + " t WHERE t.dateFin IS NULL";
        TypedQuery<Maintenance> requeteListe = em.createQuery(request, Maintenance.class);
        listeRetour = requeteListe.getResultList();
        return listeRetour;
    }

    public List<Maintenance> findTermine () {
        List<Maintenance> listeRetour = new ArrayList<>();
        String request = "SELECT t from " +this.tableName + " t WHERE t.dateFin IS NOT NULL";
        TypedQuery<Maintenance> requeteListe = em.createQuery(request, Maintenance.class);
        listeRetour = requeteListe.getResultList();
        return listeRetour;
    }
}
