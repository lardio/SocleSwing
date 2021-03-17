package fr.diginamic.swing.serviceAgence.Reservation;

import fr.diginamic.agence.dao.VehiculeDAO;
import fr.diginamic.agence.entity.client.Client;
import fr.diginamic.agence.entity.reservation.Reservation;
import fr.diginamic.agence.entity.reservation.StatutReservation;
import fr.diginamic.agence.entity.vehicule.StatutVehicule;
import fr.diginamic.agence.entity.vehicule.Vehicule;
import fr.diginamic.agence.helpers.DateHelper;
import fr.diginamic.agence.helpers.SystemOutPrintHelper;
import fr.diginamic.agence.service.ClientService;
import fr.diginamic.agence.service.ReservationService;
import fr.diginamic.agence.service.VehiculeService;
import fr.diginamic.agence.service.VoitureService;
import fr.diginamic.swing.CsvBuilder;
import fr.diginamic.swing.composants.MenuService;
import fr.diginamic.swing.composants.ui.ComboBoxStr;
import fr.diginamic.swing.composants.ui.Form;
import fr.diginamic.swing.composants.ui.TextField;
import fr.diginamic.swing.serviceAgence.EmptyValuesCheck;
import fr.diginamic.swing.serviceAgence.Reservation.Validator.ReservationFormValidator;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ListeReservationServiceSwing extends MenuService {

    protected ReservationService reservationService;
    protected VoitureService voitureService;
    protected ClientService clientService;
    protected VehiculeService vehiculeService;

    public ListeReservationServiceSwing() {
        this.reservationService = new ReservationService();
        this.voitureService = new VoitureService();
        this.clientService = new ClientService();
        this.vehiculeService = new VehiculeService(new VehiculeDAO());
    }

    @Override
    public void traitement() {
        console.clear();
        List<Reservation> listeReservations = reservationService.getAll();

        console.println("<h1 class='bg-dark-blue'><center>Liste/ajouter des reservations</center></h1>");
        String html = "";

        html += "<table class='table' cellspacing=0 >";
        html += "<tr class='bg-dark-blue'>";
        html += "<td width='150px' height='30px'><a href='ajouter()'><img width=25 src='images/plus-blue.png'></a></td>";
        html += "<td width='250px' height='30px'>Ajouter une reservation</td>";
        html += "</tr>";
        html += "<tr class='bg-dark-blue'>";
        html += "<td width='150px' height='30px'><a href='getListeCSV()'><img width=25 src='images/file-blue-xs.png'></a></td>";
        html += "<td width='250px' height='30px'>Telecharger la liste des reservations</td>";
        html += "</tr>";
        html += "</table>";

        html += "<br/>";
        html += "<table class='table' cellspacing=0> <tr class='bg-dark-blue'><td>Client</td><td>Vehicule</td><td>Date debut</td><td>Date fin</td><td>Statut</td></tr>";
        Integer counter = 0;
        String background;

        if(listeReservations != null) {
            for (Reservation resa : listeReservations) {
                counter++;
                background = counter % 2 == 0 ? "odd" : "bg-white";

                html += "<tr class='" + background  +"'>";
                html += "<td width='150px'>" + resa.getClient().getNom() +" " +resa.getClient().getPrenom() +"</td>";
                html += "<td width='250px'>" +resa.getVehicule().getNom() + " - " +resa.getVehicule().getMarque().getNom() + " " +resa.getVehicule().getModele() +" - " +resa.getVehicule().getTypeVehicule().getNom() + "</td>";
                html += "<td width='150px'>" + resa.getDateDebut() +"</td>";
                html += "<td width='150px'>" + resa.getDateFin() +"</td>";
                html += "<td width='150px'>" + resa.getStatutReservation() +"</td>";
                html += "</tr>";
            }
            html += "</table>";
        }
        console.println(html);
    }

    public void ajouter() {
        Form form = new Form();

        // Recuperation des vehicules (que voiture) existantes
        List<Vehicule> voitures = vehiculeService.getAll();
        if(voitures.size() < 1) {
            traitement();
            console.alert("Aucun véhicule enregistré, merci de créer au minimum un véhicule avant de créer une reservation.");
            return;
        }
        List<String> nomMarque = voitures.stream().map(m -> {return "" +m.getId() +" - " +m.getNom() +" - " +m.getMarque().getNom() + " " +m.getModele() +" - " +m.getTypeVehicule().getNom();}).collect(Collectors.toList());
        form.addInput(new ComboBoxStr("Vehicule:", "voiture", nomMarque, nomMarque.get(0) ));

        // Recuperation des clients
        List<Client> clients = clientService.getAll();
        if(clients.size() < 1) {
            traitement();
            console.alert("Aucun client enregistré, merci de créer au minimum un client avant de créer une reservation.");
            return;
        }
        List<String> clientsNom = clients.stream().map(m -> {return m.getId() + "- " +m.getNom() + " " +m.getPrenom();}).collect(Collectors.toList());
        form.addInput(new ComboBoxStr("Client:", "client", clientsNom, clientsNom.get(0) ));

        form.addInput(new TextField("Debut (dd/MM/yyyy):", "dateDebut"));
        form.addInput(new TextField("Fin (dd/MM/yyyy):", "dateFin"));

        List<String> listeChecks = Arrays.asList("voiture", "client", "dateDebut", "dateFin");
        EmptyValuesCheck checks = new EmptyValuesCheck(listeChecks, new ReservationFormValidator(), null);
        boolean validation = console.input("Ajout d'une reservation" , form, checks);

        if (validation) {
            Reservation reservation = new Reservation();
            String stringIdClient = form.getValue("client");
            Long idClient = Long.parseLong(stringIdClient.split("-")[0].trim());
            Client client = clientService.getById(idClient);
            reservation.setClient(client);

            String stringIdVoiture = form.getValue("voiture");
            Long idVoiture = Long.parseLong(stringIdVoiture.split("-")[0].trim());
            Vehicule voiture = (Vehicule) vehiculeService.getById(idVoiture);
            reservation.setVehicule(voiture);

            reservation.setDateDebut(DateHelper.convert(form.getValue("dateDebut")));
            reservation.setDateFin(DateHelper.convert(form.getValue("dateFin")));
            reservation.setKilometrageDebut(voiture.getKilometrage());
            if(reservation.getDateDebut().isEqual(LocalDate.now())) {
                reservation.setStatutReservation(StatutReservation.EN_COURS);
                reservation.getVehicule().setStatut(StatutVehicule.LOCATION);
            }
            SystemOutPrintHelper.messageJaune("CREATION RESERVATION");
            reservationService.create(reservation);
        }
        traitement();
    }

    public void getListeCSV() {
        List<Reservation> listeResa = reservationService.getAll();

        List<String> headers = Arrays.asList("id", "Client", "Genre de vehicule", "Marque", "Modele", "Type de vehicule", "Date debut", "Date fin", "Kilometrage debut", "Kilometrage fin", "Date retour", "Cout final");
        List<List<String>> listConteneur = new ArrayList<>();

        for (Reservation resa : listeResa) {
            List<String> listeInfos = new ArrayList<>();

            String id = "" +resa.getId();
            String prenom = resa.getClient().getNom();
            String nom = resa.getClient().getPrenom();
            String client = prenom +" " + prenom;
            String typeV = resa.getVehicule().getNom();
            String marque = resa.getVehicule().getMarque().getNom();
            String modele = resa.getVehicule().getModele();
            String type = resa.getVehicule().getTypeVehicule().getNom();
            String dateDebut = "" + resa.getDateDebut();
            String dateFin = "" + resa.getDateFin();
            String kmDebut = "" + resa.getKilometrageDebut();
            String kmFin = "" + resa.getKilometrageFin() == null ? "?" : "" + resa.getKilometrageFin();
            String dateRetour = resa.getDateRetour() == null ? "/" : "" + resa.getDateRetour();
            String cout = resa.getDateRetour() == null ? "?" : "" +resa.getFacture().getCout() + " €";
            listeInfos.add(id);
            listeInfos.add(client);
            listeInfos.add(typeV);
            listeInfos.add(marque);
            listeInfos.add(modele);
            listeInfos.add(type);
            listeInfos.add(dateDebut);
            listeInfos.add(dateFin);
            listeInfos.add(kmDebut);
            listeInfos.add(kmFin);
            listeInfos.add(dateRetour);
            listeInfos.add(cout);

            listConteneur.add(listeInfos);
        }

        CsvBuilder.creationFichierCSV("listeReservations.csv",  ",", "\n", headers, listConteneur);
        this.traitement();
        console.alert("Fichier généré. Il a été uploadé sur internet. Il est disponible a l'adresse suivante =>  https://3.139.215.128/diginamic/listeReservations.csv");
    }
}
