package fr.diginamic.agence.service;

import fr.diginamic.agence.dao.FactureDAO;
import fr.diginamic.agence.entity.reservation.Facture;
import fr.diginamic.agence.entity.reservation.Reservation;

import java.util.HashMap;
import java.util.Map;

public class FactureService extends SourceService<FactureDAO, Facture> {

    public FactureService() {
        this.entityDAO = new FactureDAO();
    }

    public Long getLastId() {
        Long id = this.entityDAO.getLastId();
        return id;
    }

    public Long getNumero() {
        Long numero = Long.valueOf(this.getAll().size() +1);
        return numero;
    }

    public void setRecap(Facture facture, Reservation reservation) {
        Map<String, String> recap = new HashMap<>();
        recap.put("prenom_emprunter", reservation.getClient().getPrenom());
        recap.put("nom_emprunter", reservation.getClient().getNom());
        recap.put("genre_vehicule", reservation.getVehicule().getNom());
        recap.put("type_vehicule", reservation.getVehicule().getTypeVehicule().getNom());
        recap.put("tarif_vehicule", "" + reservation.getVehicule().getTypeVehicule().getTarif());
        recap.put("immatriculation", reservation.getVehicule().getImmatriculation());
        recap.put("date_debut", "" +reservation.getDateDebut());
        recap.put("date_fin", "" +reservation.getDateFin());
        recap.put("date_retour", "" +reservation.getDateRetour());
        recap.put("km_debut", "" +reservation.getKilometrageDebut());
        recap.put("km_fin", "" +reservation.getKilometrageFin());
        recap.put("cout", "" + facture.getCout());
        recap.put("jours", "" + facture.getNbJourLocation());
        recap.put("numero", "" +facture.getNumero());
        facture.setRecap(recap);
    }

}
