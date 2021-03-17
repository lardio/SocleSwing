package fr.diginamic.agence.service;

import fr.diginamic.agence.dao.ReservationDAO;
import fr.diginamic.agence.entity.reservation.Facture;
import fr.diginamic.agence.entity.reservation.StatutFacture;
import fr.diginamic.agence.entity.reservation.Reservation;
import fr.diginamic.agence.entity.vehicule.StatutVehicule;
import fr.diginamic.agence.helpers.SystemOutPrintHelper;

import javax.persistence.EntityTransaction;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReservationService extends SourceService<ReservationDAO, Reservation> {

    public ReservationService() {
        this.entityDAO = new ReservationDAO();
    }

    public List<Reservation> getReservationEnded() {
        EntityTransaction tx = null;
        List<Reservation> listeReservations = this.entityDAO.listeTermine();
        return listeReservations;
    }

    public List<Reservation> getReservationNow() {
        EntityTransaction tx = null;
        List<Reservation> listeReservations = this.entityDAO.listeEnCours();
        return listeReservations;
    }

    public boolean checkInformationsCreation (Reservation reservation) {
        //date debut sup ou egal a today
        if(reservation.getDateDebut().isBefore(LocalDate.now())) {
            SystemOutPrintHelper.messageBleu("La date du debuit de la reservation est inferieir a la date d'ojd");
            return false;
        }
        //date fin sup ou egal a date debut
        if(reservation.getDateFin().isBefore(reservation.getDateDebut())) {
            SystemOutPrintHelper.messageBleu("La date de reservation fin est inférieur a la date de debut.");
            return false;
        }
        return true;
    }

    public Reservation create(Reservation reservation) {
        if (this.checkInformationsCreation(reservation)) {
            //check si date de debut = date jour.
            if(reservation.getDateDebut().isEqual(LocalDate.now())) {
                reservation.getVehicule().setStatut(StatutVehicule.LOCATION);
            }
            super.create(reservation);
            return reservation;
        } else {
            SystemOutPrintHelper.messageBleu("Creation de la reservation KO.");
            return null;
        }
    }

    public boolean checkInformationsCloture (Reservation reservation) {
        //date debut sup ou egal a today
        if(reservation.getDateDebut().isAfter(reservation.getDateRetour())) {
            SystemOutPrintHelper.messageBleu("La date de retour est inférieur a la date de debut.");
            return false;
        }
        return true;
    }

    public Reservation clotureReservation (Reservation reservation) {
        if(this.checkInformationsCloture(reservation)) {
            Facture facture = new Facture();
            facture.setReservation(reservation);
            facture.setCout(2d);
            facture.setStatut(StatutFacture.ATTENTE);
            reservation.setFacture(facture);
            this.update(reservation);
            return reservation;
        } else {
            SystemOutPrintHelper.messageBleu("MAJ de la reservation KO.");
            return null;
        }
    }

    public Map<String, String> resumeReservation(Reservation reservation, Long cout) {
        Map<String, String> mapInfos = new HashMap<>();
        mapInfos.put("Prenom emprunter", reservation.getClient().getPrenom());
        mapInfos.put("Nom emprunter", reservation.getClient().getNom());
        mapInfos.put("Genre de vehicule", reservation.getVehicule().getNom());
        mapInfos.put("Genre de vehicule", reservation.getVehicule().getNom());
        mapInfos.put("Immatriculation", reservation.getVehicule().getImmatriculation());
        mapInfos.put("Date debut de la reservation", "" +reservation.getDateDebut());
        mapInfos.put("Date fin de la reservation", "" +reservation.getDateFin());
        mapInfos.put("Date de retour du vehicule", "" +reservation.getDateRetour());
        mapInfos.put("Kilometrage debut", "" +reservation.getKilometrageDebut());
        mapInfos.put("Kilometrage au retour", "" +reservation.getKilometrageFin());
        mapInfos.put("Cout de la reservation", "" +cout);
        return mapInfos;
    }

}
