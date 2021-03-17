package fr.diginamic.swing.serviceAgence.Reservation;

import fr.diginamic.agence.dao.VehiculeDAO;
import fr.diginamic.agence.entity.agence.Entreprise;
import fr.diginamic.agence.entity.client.Client;
import fr.diginamic.agence.entity.reservation.Facture;
import fr.diginamic.agence.entity.reservation.Reservation;
import fr.diginamic.agence.entity.reservation.StatutFacture;
import fr.diginamic.agence.entity.reservation.StatutReservation;
import fr.diginamic.agence.entity.vehicule.StatutVehicule;
import fr.diginamic.agence.entity.vehicule.Vehicule;
import fr.diginamic.agence.entity.vehicule.Voiture;
import fr.diginamic.agence.helpers.DateHelper;
import fr.diginamic.agence.helpers.SystemOutPrintHelper;
import fr.diginamic.agence.service.*;
import fr.diginamic.swing.composants.MenuService;
import fr.diginamic.swing.composants.ui.ComboBoxStr;
import fr.diginamic.swing.composants.ui.Form;
import fr.diginamic.swing.composants.ui.TextField;
import fr.diginamic.swing.serviceAgence.EmptyValuesCheck;
import fr.diginamic.swing.serviceAgence.Reservation.Validator.ReservationFormValidator;
import fr.diginamic.swing.serviceAgence.Vehicule.Validator.VehiculeFormValidator;

import java.time.Duration;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class EndReservationServiceSwing extends MenuService {

    protected ReservationService reservationService;
    protected VoitureService voitureService;
    protected ClientService clientService;
    protected VehiculeService vehiculeService;
    protected FactureService factureService;
    protected EntrepriseService entrepriseService;

    public EndReservationServiceSwing() {
        this.reservationService = new ReservationService();
        this.voitureService = new VoitureService();
        this.clientService = new ClientService();
        this.vehiculeService = new VehiculeService(new VehiculeDAO());
        this.factureService = new FactureService();
        this.entrepriseService = new EntrepriseService();

    }

    @Override
    public void traitement() {
        console.clear();
        List<Reservation> listeReservations = reservationService.getReservationNow();
        SystemOutPrintHelper.messageJaune("" +listeReservations.size());

        console.println("<h1 class='bg-dark-blue'><center>Reservations en cours</center></h1>");
        String html = "";

        html += "<table class='table' cellspacing=0> <tr class='bg-dark-blue'><td>&nbsp;</td><td>Client</td><td>Vehicule</td><td>Date debut</td><td>Date fin</td><td>Statut</td></tr>";
        Integer counter = 0;
        String background;

        for (Reservation resa : listeReservations) {
            counter++;
            background = counter % 2 == 0 ? "odd" : "bg-white";

            html += "<tr class='" + background  +"'>";
            html += "<td class='bg-white'><a class='btn-red' href='terminer(" + resa.getId() + ")'><img width=25 src='images/file-blue-xs.png'></a></td>"; // TERMINER
            html += "<td width='150px'>" + resa.getClient().getNom() +" " +resa.getClient().getPrenom() +"</td>";
            html += "<td width='250px'>" +resa.getVehicule().getNom() + " - " +resa.getVehicule().getMarque().getNom() + " " +resa.getVehicule().getModele() +" - " +resa.getVehicule().getTypeVehicule().getNom() + "</td>";
            html += "<td width='150px'>" + resa.getDateDebut() +"</td>";
            html += "<td width='150px'>" + resa.getDateFin() +"</td>";
            html += "<td width='150px'>" + resa.getStatutReservation() +"</td>";
            html += "</tr>";
        }
        html += "</table>";
        console.println(html);
    }

    public void terminer(Long id) {
        Form form = new Form();

        Reservation reservation = reservationService.getById(id);

        // Recuperation des vehicules (que voiture) existantes
        Vehicule vehicule = reservation.getVehicule();
        Client client = reservation.getClient();

        form.addInput(new TextField("Date retour (dd/MM/yyyy):", "date_retour"));
        form.addInput(new TextField("Kilometrage retour:", "kilometrage_retour"));

        List<String> listeChecks = Arrays.asList("date_retour", "kilometrage_retour");
        EmptyValuesCheck checks = new EmptyValuesCheck(listeChecks, new ReservationFormValidator(), id);
        boolean validation = console.input("Cloture d'une reservation" , form, checks);

        if (validation) {
            reservation.setStatutReservation(StatutReservation.TERMINEE);
            reservation.setKilometrageDebut(Long.parseLong(form.getValue("kilometrage_retour")));
            reservation.setDateRetour(DateHelper.convert(form.getValue("date_retour")));
            reservation.getVehicule().setStatut(StatutVehicule.DISPONIBLE);
            reservation.getVehicule().setKilometrage(Long.parseLong(form.getValue("kilometrage_retour")));

            Facture facture = new Facture();
            facture.setClient(client);
            facture.setReservation(reservation);

            Long days = reservation.getDateDebut().until(reservation.getDateRetour(), ChronoUnit.DAYS);
            Double coutFinal = (days + 1) * vehicule.getTypeVehicule().getTarif();
            facture.setNbJourLocation(days + 1);
            facture.setCout(coutFinal);
            facture.setStatut(StatutFacture.ATTENTE);
            facture.setNumero(factureService.getNumero());
            factureService.setRecap(facture, reservation);
            reservation.setFacture(facture);
            reservationService.update(reservation);

            Entreprise entreprise = entrepriseService.getById(1l);
            entreprise.setCompta(entreprise.getCompta() + facture.getCout());
            entrepriseService.update(entreprise);
        }
        traitement();
    }
}
