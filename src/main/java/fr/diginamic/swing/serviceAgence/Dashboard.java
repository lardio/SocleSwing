package fr.diginamic.swing.serviceAgence;

import fr.diginamic.agence.dao.VehiculeDAO;
import fr.diginamic.agence.entity.agence.Entreprise;
import fr.diginamic.agence.entity.reservation.Reservation;
import fr.diginamic.agence.service.*;
import fr.diginamic.swing.composants.MenuService;

import java.util.List;

public class Dashboard extends MenuService {

    protected ReservationService reservationService;
    protected VoitureService voitureService;
    protected ClientService clientService;
    protected VehiculeService vehiculeService;
    protected EntrepriseService entrepriseService;
    protected MaintenanceService maintenanceService;

    public Dashboard() {
        this.reservationService = new ReservationService();
        this.voitureService = new VoitureService();
        this.clientService = new ClientService();
        this.vehiculeService = new VehiculeService(new VehiculeDAO());
        this.entrepriseService = new EntrepriseService();
        this.maintenanceService = new MaintenanceService();
    }

    @Override
    public void traitement() {
        console.clear();
        Entreprise entreprise = entrepriseService.getById(1l);

        console.println("<h1 class='bg-dark-blue'><center>INFORMATIONS SUR L'ENTREPRISE</center></h1>");
        console.println("<h1 class='bg-dark-blue'><center>"+ entreprise.getNom() +"</center></h1>");
        console.println("<h1 class='bg-dark-blue'><center>Resultat de l'entreprise =>   "+ entreprise.getCompta() +" €</center></h1>");
        String html = "";

        html += "<br/>";
        html += "<table class='table' cellspacing=0> <tr class='bg-dark-blue'><td>Nb Vehicules</td><td>Nb Clients</td><td>Nb reservations en cours</td><td>Nb maintenance en cours</td></tr>";

        html += "<tr>";
        html += "<td width='150px'>" + vehiculeService.getAll().size() +"</td>";
        html += "<td width='150px'>" + clientService.getAll().size() + "</td>";
        html += "<td width='150px'>" + reservationService.getAll().size() +"</td>";
        html += "<td width='150px'>" + maintenanceService.getAll().size() +"</td>";
        html += "</tr>";

        html += "</table>";

        console.println(html);
    }
}
