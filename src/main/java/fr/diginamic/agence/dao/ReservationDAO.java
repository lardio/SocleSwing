package fr.diginamic.agence.dao;
import fr.diginamic.agence.entity.reservation.Reservation;

import javax.persistence.TypedQuery;
import java.time.LocalDate;
import java.util.List;

public class ReservationDAO extends SourceDAO<Reservation> {

    public ReservationDAO() {
        super(Reservation.class);
        this.tableName = Reservation.class.getSimpleName();
    }

    public List<Reservation> listeTermine() {
        String request = "SELECT t from " +this.tableName + " t WHERE t.dateFin < ?0";
        TypedQuery<Reservation> requeteListe = em.createQuery(request, Reservation.class);
        requeteListe.setParameter(0, LocalDate.now());
        List<Reservation> resultFromDB = requeteListe.getResultList();
        return resultFromDB;
    }

    public List<Reservation> listeEnCours() {
        String request = "SELECT t from " +this.tableName + " t WHERE t.dateRetour IS NULL";
        TypedQuery<Reservation> requeteListe = em.createQuery(request, Reservation.class);
        List<Reservation> resultFromDB = requeteListe.getResultList();
        return resultFromDB;
    }

}