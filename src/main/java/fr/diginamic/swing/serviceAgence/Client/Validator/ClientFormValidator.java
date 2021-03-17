package fr.diginamic.swing.serviceAgence.Client.Validator;

import fr.diginamic.agence.entity.client.Adresse;
import fr.diginamic.agence.entity.client.TypePermis;
import fr.diginamic.agence.entity.vehicule.Marque;
import fr.diginamic.agence.helpers.SystemOutPrintHelper;
import fr.diginamic.agence.service.AdresseService;
import fr.diginamic.agence.service.MarqueService;
import fr.diginamic.agence.service.TypePermisService;
import fr.diginamic.swing.composants.Console;
import fr.diginamic.swing.serviceAgence.AgenceFormValidator;

public class ClientFormValidator extends AgenceFormValidator {

    @Override
    public boolean checkValueFromForm(String field, String value, Long id, Console console) {
        if( field.equals("nom") || field.equals("prenom")) {
            if (value.matches("[a-zA-Z]*(-[a-zA-Z]*)?")) {
                SystemOutPrintHelper.messageJaune("Champ retourne nom/premnom OK.");
            } else {
                SystemOutPrintHelper.messageBleu("Champ retourne nom/premnom au mauvais format.");
                console.alert("Le format du nom ou prenom est invalide.");
                return false;
            }
        }

        //ADRESSE
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

            AdresseService adresseService = new AdresseService();
            Adresse adresse = adresseService.getByKey("mail", value);
            if(adresse != null && adresse.getId() != id) {
                SystemOutPrintHelper.messageBleu("Adresse mail existe deja.");
                console.alert("L'adresse mail est déjà utilisée.");
                return false;
            }
        }


        //PERMIS
        if( field.equals("typeP") ) {
            TypePermisService typePermisService = new TypePermisService();
            TypePermis typePermis = typePermisService.getByKey("nom", value);
            if(typePermis != null) {
                SystemOutPrintHelper.messageJaune("Champ retourné typePermis OK.");
                return true;
            } else {
                SystemOutPrintHelper.messageBleu("Champ retourne typePermis inexistant dans la BDD.");
                console.alert("Le type de permis doit correspondre a un type existant. Merci de d'abord ajouter le type si necessaire.");
                return false;
            }
        }

        if( field.equals("numP") ) {
            if( value.matches("[0-9]*") ) {
                SystemOutPrintHelper.messageJaune("Champ retourné numero Permis OK.");
                return true;
            } else {
                SystemOutPrintHelper.messageBleu("Champ retourne numPermis KO.");
                console.alert("Le numero de permis est au format incorrect");
                return false;
            }
        }

        if( field.equals("dateP") ) {
            if (value.matches("[0-2][0-9]/[0-1][0-9]/[1-2][0-9]{3}")) {
                SystemOutPrintHelper.messageJaune("Champ retourne date permis OK.");
            } else {
                SystemOutPrintHelper.messageBleu("Champ retourne date permis au mauvais format.");
                console.alert("Le format de la date d'obtention du permis est pas bonne ou incoherente.");
                return false;
            }
        }

        return true;
    }
}
