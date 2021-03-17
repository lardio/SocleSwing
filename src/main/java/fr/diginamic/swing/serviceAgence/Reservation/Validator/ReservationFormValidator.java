package fr.diginamic.swing.serviceAgence.Reservation.Validator;

import fr.diginamic.agence.dao.VehiculeDAO;
import fr.diginamic.agence.entity.client.Client;
import fr.diginamic.agence.entity.reservation.Reservation;
import fr.diginamic.agence.entity.vehicule.Maintenance;
import fr.diginamic.agence.entity.vehicule.Vehicule;
import fr.diginamic.agence.entity.vehicule.Voiture;
import fr.diginamic.agence.helpers.DateHelper;
import fr.diginamic.agence.helpers.SystemOutPrintHelper;
import fr.diginamic.agence.service.*;
import fr.diginamic.swing.composants.Console;
import fr.diginamic.swing.serviceAgence.AgenceFormValidator;

import java.time.LocalDate;

public class ReservationFormValidator extends AgenceFormValidator {

    protected LocalDate dateDebut;

    @Override
    public boolean checkValueFromForm(String field, String value, Long id, Console console) {
        //Check de la date au bon format
        if( field.equals("dateDebut") || field.equals("dateFin") || field.equals("date_retour") ){
            if (value.matches("[0-2][0-9]/[0-1][0-9]/[1-2][0-9]{3}")) {
                SystemOutPrintHelper.messageJaune("Champ retourne date OK.");
            } else {
                SystemOutPrintHelper.messageBleu("Champ retourne date debut au mauvais format.");
                console.alert("Le format de la date est pas bon ou non coherent.");
                return false;
            }
        }

        if( field.equals("dateDebut")) {
            LocalDate dateCheck = DateHelper.convert(value);
            if(dateCheck.isBefore(LocalDate.now())) {
                SystemOutPrintHelper.messageBleu("Date reservation inferieur a date du jour.");
                console.alert("La date de debut de la resa est inferieur a la date du jour.");
                return false;
            }
            this.dateDebut = dateCheck;
            SystemOutPrintHelper.messageJaune("Date debut resa OK.");
        }

        if( field.equals("dateFin")) {
            LocalDate dateCheck = DateHelper.convert(value);
            if(dateCheck.isBefore(this.dateDebut)) {
                SystemOutPrintHelper.messageBleu("Date fin reservation inferieur a date debut.");
                console.alert("La date de fin de la resa est inferieur a la date de debut.");
                return false;
            }
            SystemOutPrintHelper.messageJaune("Date fin resa OK.");
        }

        if( field.equals("date_retour") || field.equals("kilometrage_retour") ) {
            ReservationService reservationService = new ReservationService();
            Reservation reservation = reservationService.getById(id);
            if( field.equals("date_retour")) {
                LocalDate dateCheck = DateHelper.convert(value);
                if(dateCheck.isBefore(reservation.getDateDebut())) {
                    SystemOutPrintHelper.messageBleu("Date retour  inferieur a date debut.");
                    console.alert("La date de retour est inferieur a la date de debut.");
                    return false;
                }
                if(dateCheck.isAfter(LocalDate.now())) {
                    SystemOutPrintHelper.messageBleu("Date retour superieur a la date ojd.");
                    console.alert("La date de retour est superieur a aujourd'hui.");
                    return false;
                }
            }

            if( field.equals("kilometrage_retour")) {
                if(reservation.getKilometrageDebut() > Long.parseLong(value)) {
                    SystemOutPrintHelper.messageBleu("Kilometrage retour  inferieur au kilometrage debut.");
                    console.alert("La kilometrage de retour est inferieur a celui au moment du debut de la reservation.");
                    return false;
                }
            }
        }

        if( field.equals("client")) {
            Long idClient = Long.parseLong(value.split("-")[0].trim());
            SystemOutPrintHelper.messageJaune("ID CLIENT => " + idClient);
            ClientService clientService = new ClientService();
            Client client = clientService.getById(idClient);
            if(client != null) {
                SystemOutPrintHelper.messageJaune("Client OK.");
            } else {
                SystemOutPrintHelper.messageBleu("Client resa KO.");
                console.alert("Le client selectionne existe pas.");
                return false;
            }
        }

        if( field.equals("voiture")) {
            Long idVoiture = Long.parseLong(value.split("-")[0].trim());
            SystemOutPrintHelper.messageJaune("ID VOITURE => " + idVoiture);
            VehiculeService voitureService = new VehiculeService(new VehiculeDAO());
            Vehicule voiture = (Vehicule) voitureService.getById(idVoiture);
            if(voiture != null) {
                SystemOutPrintHelper.messageJaune("Vehicule OK.");
            } else {
                SystemOutPrintHelper.messageBleu("voiture resa KO.");
                console.alert("Le vehicule selectionné existe pas.");
                return false;
            }
        }

        return true;
    }
}
