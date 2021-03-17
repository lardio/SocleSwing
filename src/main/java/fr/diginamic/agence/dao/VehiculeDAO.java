package fr.diginamic.agence.dao;

import fr.diginamic.agence.entity.vehicule.Vehicule;
import fr.diginamic.agence.entity.reservation.Reservation;

import javax.persistence.TypedQuery;
import java.util.List;

public class VehiculeDAO<T extends Vehicule> extends SourceDAO {

    public VehiculeDAO() {
        super(Vehicule.class);
        this.tableName = Vehicule.class.getSimpleName();
    }

    public List<Reservation> checkReservation(T vehicule) {
        String request = "SELECT r from " + Reservation.class.getSimpleName() +" r WHERE r.vehicule = ?0";
        TypedQuery<Reservation> finalRequest = em.createQuery(request, Reservation.class);
        finalRequest.setParameter(0, vehicule);
        List<Reservation> reservations  = finalRequest.getResultList();
        return reservations;
    }

}
