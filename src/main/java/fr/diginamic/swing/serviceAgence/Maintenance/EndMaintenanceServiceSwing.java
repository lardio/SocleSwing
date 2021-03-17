package fr.diginamic.swing.serviceAgence.Maintenance;

import fr.diginamic.agence.dao.VehiculeDAO;
import fr.diginamic.agence.entity.agence.Entreprise;
import fr.diginamic.agence.entity.vehicule.Maintenance;
import fr.diginamic.agence.entity.vehicule.StatutVehicule;
import fr.diginamic.agence.helpers.DateHelper;
import fr.diginamic.agence.helpers.SystemOutPrintHelper;
import fr.diginamic.agence.service.EntrepriseService;
import fr.diginamic.agence.service.MaintenanceService;
import fr.diginamic.agence.service.VehiculeService;
import fr.diginamic.swing.composants.MenuService;
import fr.diginamic.swing.composants.ui.Form;
import fr.diginamic.swing.composants.ui.TextField;
import fr.diginamic.swing.serviceAgence.EmptyValuesCheck;
import fr.diginamic.swing.serviceAgence.Maintenance.Validator.MaintenanceFormValidator;

import java.util.Arrays;
import java.util.List;

public class EndMaintenanceServiceSwing extends MenuService {

    protected VehiculeService vehiculeService;
    protected MaintenanceService maintenanceService;
    protected EntrepriseService entrepriseService;

    public EndMaintenanceServiceSwing () {
        this.vehiculeService = new VehiculeService(new VehiculeDAO());
        this.maintenanceService = new MaintenanceService();
        this.entrepriseService = new EntrepriseService();
    }

    @Override
    public void traitement() {
        console.clear();

        List<Maintenance> listeMaintenance = maintenanceService.listeEnCours();
        console.println("<h1 class='bg-dark-blue'><center>Maintenance en cours</center></h1>");
        Integer counter = 0;
        String background;

        String html = "<table class='table' cellspacing=0> <tr class='bg-dark-blue'><td>&nbsp;</td><td>Type</td><td>Marque</td><td>Modele</td><td>Immatriculation</td><td>Kilometrage</td><td>Status</td><td>Date debut maintenance</td></tr>";

        for (Maintenance maintenance : listeMaintenance) {
            counter++;
            background = counter % 2 == 0 ? "odd" : "bg-white";
            html += "<tr height='80px' class='" + background  +"'>";
            html += "<td><a class='btn-blue' href='finir(" + maintenance.getId() +")'><img width=25 src='images/pencil-blue-xs.png'></a></td>";
            html += "<td width='120px'>" + maintenance.getVehicule().getNom() + "</td>";
            html += "<td width='120px'>" + maintenance.getVehicule().getMarque().getNom() + "</td>";
            html += "<td width='120px'>" + maintenance.getVehicule().getModele() + "</td>";
            html += "<td width='120px'>" + maintenance.getVehicule().getImmatriculation() + "</td>";
            html += "<td width='120px'>" + maintenance.getVehicule().getKilometrage() + "</td>";
            html += "<td width='120px'>" + maintenance.getVehicule().getStatut() + "</td>" ;
            html += "<td width='120px'>" + maintenance.getDateDebut().toString() + "</td>" + "</tr>";
        }
        html += "</table>";
        console.println(html);
    }

    public void finir(Long id) {
        Form form = new Form();

        form.addInput(new TextField("Fin (dd/MM/yyyy):", "dateFin"));
        form.addInput(new TextField("Cout des reparations:", "cout"));
        List<String> listeChecks = Arrays.asList("cout", "dateFin");

        EmptyValuesCheck checks = new EmptyValuesCheck(listeChecks, new MaintenanceFormValidator(), id);
        boolean validation = console.input("Ajout d'une maintenance", form, checks);

        if (validation) {
            Maintenance maintenance = maintenanceService.getById(id);
            maintenance.setDateFin(DateHelper.convert(form.getValue("dateFin")) );
            maintenance.setCout(Double.parseDouble(form.getValue("cout")) );
            SystemOutPrintHelper.messageJaune(""+maintenance.getDateDebut());
            maintenance.getVehicule().setStatut(StatutVehicule.DISPONIBLE);
            vehiculeService.update(maintenance.getVehicule());
            maintenanceService.update(maintenance);
            Entreprise entreprise = entrepriseService.getById(1l);
            entreprise.setCompta(entreprise.getCompta() - maintenance.getCout());
            entrepriseService.update(entreprise);
        }
        traitement();


    }
}
