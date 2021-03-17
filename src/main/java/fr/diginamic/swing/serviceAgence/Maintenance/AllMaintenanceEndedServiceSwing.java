package fr.diginamic.swing.serviceAgence.Maintenance;

import fr.diginamic.agence.dao.VehiculeDAO;
import fr.diginamic.agence.entity.vehicule.Maintenance;
import fr.diginamic.agence.service.MaintenanceService;
import fr.diginamic.agence.service.VehiculeService;
import fr.diginamic.swing.composants.MenuService;

import java.util.List;

public class AllMaintenanceEndedServiceSwing extends MenuService {

    protected VehiculeService vehiculeService;
    protected MaintenanceService maintenanceService;

    public AllMaintenanceEndedServiceSwing () {
        this.vehiculeService = new VehiculeService(new VehiculeDAO());
        this.maintenanceService = new MaintenanceService();
    }

    @Override
    public void traitement() {
        console.clear();

        Integer counter = 0;
        String background;

        List<Maintenance> listeMaintenance = maintenanceService.listeTermine();
        console.println("<h1 class='bg-dark-blue'><center>Maintenances terminées</center></h1>");
        String html = "<table class='table' cellspacing=0> <tr class='bg-dark-blue'><td>Type</td><td>Marque</td><td>Modele</td><td>Immatriculation</td><td>Kilometrage</td><td>Status</td><td>Cout</td><td>Date debut maintenance</td><td>Date fin maintenance</td></tr>";
        for (Maintenance maintenance : listeMaintenance) {
            counter++;
            background = counter % 2 == 0 ? "odd" : "bg-white";
            html += "<tr height='80px' class='" + background  +"'>";
            html += "<td width='100px'>" + maintenance.getVehicule().getNom() + "</td>";
            html += "<td width='100px'>" + maintenance.getVehicule().getMarque().getNom() + "</td>";
            html += "<td width='120px'>" + maintenance.getVehicule().getModele() + "</td>";
            html += "<td width='100px'>" + maintenance.getVehicule().getImmatriculation() + "</td>";
            html += "<td width='100px'>" + maintenance.getVehicule().getKilometrage() + "</td>";
            html += "<td width='110px'>" + maintenance.getVehicule().getStatut() + "</td>" ;
            html += "<td width='100px'>" + maintenance.getCout() + "</td>" ;
            html += "<td width='110px'>" + maintenance.getDateDebut().toString() + "</td>";
            html += "<td width='110px'>" + maintenance.getDateFin().toString() + "</td>" + "</tr>";
        }
        html += "</table>";
        console.println(html);
    }

}
