package fr.diginamic.swing.serviceAgence.Client.Validator;

import fr.diginamic.agence.helpers.SystemOutPrintHelper;
import fr.diginamic.swing.composants.Console;
import fr.diginamic.swing.serviceAgence.AgenceFormValidator;

public class PermisFormValidator extends AgenceFormValidator {

    @Override
    public boolean checkValueFromForm(String field, String value, Long id, Console console) {
        if( field.equals("nom")) {
            if (value.matches("[a-zA-Z0-9]*(\\s[a-zA-Z0-9])*")) {
                SystemOutPrintHelper.messageJaune("Champ nom permis OK.");
            } else {
                SystemOutPrintHelper.messageBleu("Champ nom permis KO.");
                console.alert("Le format du nom de permis est invalide.");
                return false;
            }
        }
        return true;
    }
}
