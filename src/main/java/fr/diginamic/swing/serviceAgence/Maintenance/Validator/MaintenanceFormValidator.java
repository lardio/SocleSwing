package fr.diginamic.swing.serviceAgence.Maintenance.Validator;

import fr.diginamic.agence.entity.vehicule.Maintenance;
import fr.diginamic.agence.helpers.DateHelper;
import fr.diginamic.agence.helpers.SystemOutPrintHelper;
import fr.diginamic.agence.service.MaintenanceService;
import fr.diginamic.swing.composants.Console;
import fr.diginamic.swing.serviceAgence.AgenceFormValidator;

public class MaintenanceFormValidator extends AgenceFormValidator {

    @Override
    public boolean checkValueFromForm(String field, String value, Long id, Console console) {
        if( field.equals("cout")) {
            if (value.matches("[0-9]*(.[0-9]{1,2})?")) {
                SystemOutPrintHelper.messageJaune("Champ retourne cout OK.");
            } else {
                SystemOutPrintHelper.messageBleu("Champ retourne cout maintenant  au mauvais format.");
                console.alert("Le format du cout est invalide.");
                return false;
            }
        }

        //Check de la date au bon format
        if( field.equals("dateDebut") || field.equals("dateFin")) {
            if (value.matches("[0-2][0-9]/[0-1][0-9]/[1-2][0-9]{3}")) {
                SystemOutPrintHelper.messageJaune("Champ retourne date OK.");
            } else {
                SystemOutPrintHelper.messageBleu("Champ retourne date debut au mauvais format.");
                console.alert("Le format de la date est pas bon ou non coherente.");
                return false;
            }
        }

        if( field.equals("dateFin")) {
            MaintenanceService maintenanceService = new MaintenanceService();
            Maintenance maintenance = maintenanceService.getById(id);
            maintenance.setDateFin(DateHelper.convert(value));
            return maintenanceService.checkInfo(maintenance, console);
        }
        return true;
    }

}