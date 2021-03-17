package fr.diginamic.swing.serviceAgence.Maintenance;

import fr.diginamic.agence.dao.VehiculeDAO;
import fr.diginamic.agence.entity.vehicule.Maintenance;
import fr.diginamic.agence.entity.vehicule.StatutVehicule;
import fr.diginamic.agence.entity.vehicule.Vehicule;
import fr.diginamic.agence.helpers.DateHelper;
import fr.diginamic.agence.helpers.SystemOutPrintHelper;
import fr.diginamic.agence.service.MaintenanceService;
import fr.diginamic.agence.service.TypeVehiculeService;
import fr.diginamic.agence.service.VehiculeService;
import fr.diginamic.swing.composants.MenuService;
import fr.diginamic.swing.composants.ui.Form;
import fr.diginamic.swing.composants.ui.TextField;
import fr.diginamic.swing.serviceAgence.EmptyValuesCheck;
import fr.diginamic.swing.serviceAgence.Maintenance.Validator.MaintenanceFormValidator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MaintenanceServiceSwing extends MenuService {

    protected VehiculeService vehiculeService;
    protected MaintenanceService maintenanceService;
    protected TypeVehiculeService typeVehiculeService;

    public MaintenanceServiceSwing () {

        this.vehiculeService = new VehiculeService(new VehiculeDAO());
        this.maintenanceService = new MaintenanceService();
        this.typeVehiculeService = new TypeVehiculeService();
    }

    @Override
    public void traitement() {
        List<Vehicule> listeVehicules = vehiculeService.getAll();
        console.clear();
        console.println("<h1 class='bg-dark-blue'><center>Liste des vehicules pour ajouter une maintenance</center></h1>");

        String html = "";
        SystemOutPrintHelper.messageJaune(listeVehicules.size() + "");
        if(listeVehicules.size() > 0) {
            Integer counter = 0;
            String background;

            html += "<table class='table' cellspacing=0> <tr class='bg-dark-blue'><td>&nbsp;</td><td>Type</td><td>Marque</td><td>Modele</td><td>Immatriculation</td><td>Kilometrage</td><td>Status</td></tr>";
            for (Vehicule vehicule : listeVehicules) {

                if(vehicule.getStatut().equals(StatutVehicule.DISPONIBLE)) {
                    counter++;
                    background = counter % 2 == 0 ? "odd" : "bg-white";
                    html += "<tr height='80px' class='" + background  +"'>";
                    html += "<td><a class='btn-blue' href='ajouter(" + vehicule.getId() +")'><img width=25 src='images/plus-blue.png'></a></td>";
                    html += "<td width='120px'>" + vehicule.getNom() + "</td>";
                    html += "<td width='120px'>" + vehicule.getMarque().getNom() + "</td>";
                    html += "<td width='120px'>" + vehicule.getModele() + "</td>";
                    html += "<td width='120px'>" + vehicule.getImmatriculation() + "</td>";
                    html += "<td width='120px'>" + vehicule.getKilometrage() + "</td>";
                    html += "<td width='120px'>" + vehicule.getStatut() + "</td>" + "</tr>";
                }
            }
            html += "</table>";
        } else {
            console.println("<h1 class='bg-dark-blue'><center>L'entreprise n'a aucun véhicule, donc aucune maintenance possible</center></h1>");
        }

        console.println(html);
    }


    public void ajouter(Long id) {
        Form form = new Form();
        form.addInput(new TextField("Debut (dd/MM/yyyy):", "dateDebut"));

        List<String> listeChecks = Arrays.asList("dateDebut");
        EmptyValuesCheck checks = new EmptyValuesCheck(listeChecks, new MaintenanceFormValidator(), null);
        boolean validation = console.input("Ajout d'une maintenance", form, checks);
        if (validation) {
            Maintenance maintenance = new Maintenance();
            maintenance.setDateDebut(DateHelper.convert(form.getValue("dateDebut")));
            maintenance.setVehicule((Vehicule)vehiculeService.getById(id));
            maintenance.getVehicule().setStatut(StatutVehicule.MAINTENANCE);
            maintenanceService.create(maintenance);
        }
        traitement();
    }
}
