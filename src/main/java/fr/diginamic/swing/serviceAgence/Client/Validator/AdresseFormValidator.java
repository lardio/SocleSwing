package fr.diginamic.swing.serviceAgence.Client.Validator;

import fr.diginamic.agence.helpers.SystemOutPrintHelper;
import fr.diginamic.swing.composants.Console;
import fr.diginamic.swing.serviceAgence.AgenceFormValidator;

public class AdresseFormValidator extends AgenceFormValidator {

    @Override
    public boolean checkValueFromForm(String field, String value, Long id, Console console) {
        if( field.equals("ville")) {
            if (value.matches("^([a-zA-Z\\u0080-\\u024F]+(?:. |-| |'))*[a-zA-Z\\u0080-\\u024F]*$")) {
                SystemOutPrintHelper.messageJaune("Champ retourne ville OK.");
            } else {
                SystemOutPrintHelper.messageBleu("Champ retourne ville au mauvais format.");
                console.alert("Le format de la ville est invalide.");
                return false;
            }
        }

        if( field.equals("code")) {
            if (value.matches("[0-9]{5}")) {
                SystemOutPrintHelper.messageJaune("Champ retourne code postal OK.");
            } else {
                SystemOutPrintHelper.messageBleu("Champ retourne code postal  au mauvais format.");
                console.alert("Le format du code postal est invalide (ex :85000).");
                return false;
            }
        }

        if( field.equals("num")) {
            if (value.matches("[0-9]{1,4}")) {
                SystemOutPrintHelper.messageJaune("Champ retourne num rue OK.");
            } else {
                SystemOutPrintHelper.messageBleu("Champ retourne num rue au mauvais format.");
                console.alert("Le format du numero de rue est invalide.");
                return false;
            }
        }

        if( field.equals("rue")) {
            if (value.matches("^([a-zA-Z\\u0080-\\u024F]+(?:. |-| |'))*[a-zA-Z\\u0080-\\u024F]*$")) {
                SystemOutPrintHelper.messageJaune("Champ retourne nom rue OK.");
            } else {
                SystemOutPrintHelper.messageBleu("Champ retourne nom rue au mauvais format.");
                console.alert("Le format du nom de la de rue est invalide.");
                return false;
            }
        }

        if( field.equals("tel")) {
            if( value.matches("^0[1-9][0-9]{8}$") ) {
                SystemOutPrintHelper.messageJaune("Format telephone OK");
            } else {
                SystemOutPrintHelper.messageBleu("Le format du numero telephone donné est incorrect.");
                console.alert("Le format du numero telephone donné est incorrect.");
                return false;
            }
        }

        if( field.equals("mail")) {
            if( value.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$") ) {
                SystemOutPrintHelper.messageJaune("Format mail OK");
            } else {
                SystemOutPrintHelper.messageBleu("Le format de l'adresse mail donnée est incorrecte.");
                console.alert("Le format du mail donné est incorrect.");
                return false;
            }
        }
        return true;
    }
}