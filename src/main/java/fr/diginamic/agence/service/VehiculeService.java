package fr.diginamic.agence.service;

import fr.diginamic.agence.EntityManagerGestion;
import fr.diginamic.agence.Exception.DAOException;
import fr.diginamic.agence.dao.VehiculeDAO;
import fr.diginamic.agence.entity.reservation.Reservation;
import fr.diginamic.agence.entity.vehicule.Vehicule;
import fr.diginamic.agence.helpers.SystemOutPrintHelper;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;

public class VehiculeService<T extends VehiculeDAO, E extends Vehicule> extends SourceService<T, E> {

    public VehiculeService(T classeDAO) {
        super();
        this.entityDAO = classeDAO;
    }

    public VehiculeService() {
        super();
    }

    /*
    public boolean checkImmatriculation(E vehicule) {
        return this.checkImmatriculation(vehicule, false);
    }

    public boolean checkImmatriculation(E vehicule, boolean consoleMessage) throws DAOException {
        System.out.println(vehicule.getClass());
        EntityTransaction tx = null;
        try {
            Console console = null;
            if(consoleMessage) {console = new Console();}

            Object check = (E) this.entityDAO.findByKey("immatriculation", vehicule.getImmatriculation());
            if( vehicule.getImmatriculation().matches("[a-zA-Z_0-9]{2}-[a-zA-Z_0-9]{3}-[a-zA-Z_0-9]{2}$") ) {
                if (Objects.isNull(check)) {
                    SystemOutPrintHelper.messageBleu("Immatriculation au bon format");
                    return true;
                } else {
                    SystemOutPrintHelper.messageJaune("Echec de creation d'un vehicule, l'immatriculation donnée est déjà assignée à un autre véhicule.");
                    if(consoleMessage) {console.alert("Immatriculation déjà utilisé"); }
                    return false;
                }
            } else {
                SystemOutPrintHelper.messageJaune("Echec de creation d'un vehicule, l'immatriculation donnée est au mauvais format.");
                if(consoleMessage) {console.alert("Immatriculation au mauvais format."); }
                return false;
            }
        } catch (Exception e) {
            if (tx!=null) {
                tx.rollback();
            }
            throw new DAOException(e);
        }
    }
    */

    public E create(E vehicule) {
        EntityTransaction tx = null;
        try {
            EntityManager em = new EntityManagerGestion().recuperationEntityManager();
            tx = em.getTransaction();
            tx.begin();
            entityDAO.add(vehicule);
            tx.commit();
        } catch (Exception e) {
            if (tx!=null) {
                tx.rollback();
            }
            throw new DAOException(e);
        }
        return vehicule;
    }

    /**
     * Verifie si le vehicule à déjà eu une reservation (en cours ou terminée°.
     * @see Reservation
     * @param vehicule
     * @return boolean
     */
    public boolean checkIfAlreadyHasReservation(E vehicule) {
        List<Reservation> reservations = this.entityDAO.checkReservation(vehicule);
        if(reservations.isEmpty()) {
            SystemOutPrintHelper.messageJaune("Aucune reservation pour ce vehicule");
            return false;
        }
        SystemOutPrintHelper.messageJaune("Ce véhicule à déjà eu une reservation");
        return true;
    }


}
