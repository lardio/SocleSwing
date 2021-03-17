package fr.diginamic.agence.service;

import fr.diginamic.agence.dao.MaintenanceDAO;
import fr.diginamic.agence.dao.VehiculeDAO;
import fr.diginamic.agence.entity.vehicule.Maintenance;
import fr.diginamic.agence.entity.vehicule.StatutVehicule;
import fr.diginamic.agence.helpers.SystemOutPrintHelper;
import fr.diginamic.swing.composants.Console;
import org.jboss.jandex.Main;

import java.time.LocalDate;
import java.util.List;

public class MaintenanceService extends SourceService<MaintenanceDAO, Maintenance> {

    public MaintenanceService() {
        this.entityDAO = new MaintenanceDAO();
    }

    public List<Maintenance> listeEnCours() {
        List<Maintenance> maintenances = this.entityDAO.findEnCours();
        return maintenances;
    }

    public List<Maintenance> listeTermine() {
        List<Maintenance> maintenances = this.entityDAO.findTermine();
        return maintenances;
    }

    public boolean checkInfo(Maintenance maintenance, Console console) {
        if(maintenance.getDateFin().isAfter(LocalDate.now())) {
            SystemOutPrintHelper.messageBleu("La date est superieur a la date d'ojd");
            console.alert("La date de fin est superieur a la date d'ojd");
            return false;
        }
        //check si inf a dateDebut
        if(maintenance.getDateDebut().isAfter(maintenance.getDateFin())) {
            SystemOutPrintHelper.messageBleu("La date de fin est inferieur a la date de debut de la maintenance");
            console.alert("La date de fin est inferieur a la date de debut de la maintenance");
            return false;
        }
        return true;
    }

    public Maintenance createStart(Maintenance maintenance) {
        //Si véhicule dispo
        if(maintenance.getVehicule().getStatut() != StatutVehicule.DISPONIBLE) {
            SystemOutPrintHelper.messageBleu("Véhicule n'est pas disponible !");
            return null;
        }

        SystemOutPrintHelper.messageJaune("Véhicule disponible");
        //MAJ vehicule
        maintenance.getVehicule().setStatut(StatutVehicule.MAINTENANCE);
        VehiculeService vehiculeService = new VehiculeService(new VehiculeDAO());
        this.create(maintenance);

        return maintenance;
    }

    public Maintenance createEnd(Maintenance maintenance) {
        //check si sup a now
        if(maintenance.getDateFin().isAfter(LocalDate.now())) {
            SystemOutPrintHelper.messageBleu("La date est superieur a la date d'ojd");
            return null;
        }
        //check si inf a dateDebut
        if(maintenance.getDateDebut().isAfter(maintenance.getDateFin())) {
            SystemOutPrintHelper.messageBleu("La date est inferieur a la date de debut de la maintenance");
            return null;
        }
        maintenance.getVehicule().setStatut(StatutVehicule.DISPONIBLE);
        this.update(maintenance);
        return maintenance;
    }

}
