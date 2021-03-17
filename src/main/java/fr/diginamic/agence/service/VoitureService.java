package fr.diginamic.agence.service;

import fr.diginamic.agence.dao.VoitureDAO;
import fr.diginamic.agence.entity.vehicule.Voiture;

public class VoitureService extends VehiculeService<VoitureDAO, Voiture> {

    public VoitureService() { this.entityDAO = new VoitureDAO(); }
    /*
    public boolean checkImmatriculation(Voiture voiture) throws DAOException {
        EntityTransaction tx = null;
        try {
            Voiture check = this.entityDAO.findByKey("immatriculation", voiture.getImmatriculation());
            if(Objects.isNull(check)){
                System.out.println("Ima ok");
                return true;
            } else {
                throw new DAOException(" Echec de creation d'un vehicule, l'immatriculation donnée est déjà assignée à un autre véhicule.");
            }
        } catch (Exception e) {
            if (tx!=null) {
                tx.rollback();
            }
            throw new DAOException(e);
        }
    }

    public Voiture createVehicule(Voiture voiture) {
        EntityTransaction tx = null;
        try {
            if(checkImmatriculation(voiture)) {
                this.create(voiture);
            } else {
                return voiture;
            }
        } catch (Exception e) {
            if (tx!=null) {
                tx.rollback();
            }
            throw new DAOException(e);
        }
        return voiture;
    }
    */


}
