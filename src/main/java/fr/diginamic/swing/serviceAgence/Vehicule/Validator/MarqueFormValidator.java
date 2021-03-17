package fr.diginamic.swing.serviceAgence.Vehicule.Validator;

import fr.diginamic.agence.entity.vehicule.Marque;
import fr.diginamic.agence.helpers.SystemOutPrintHelper;
import fr.diginamic.agence.service.MarqueService;
import fr.diginamic.swing.composants.Console;
import fr.diginamic.swing.serviceAgence.AgenceFormValidator;

public class MarqueFormValidator extends AgenceFormValidator {

    @Override
    public boolean checkValueFromForm(String field, String value, Long id, Console console) {
        //Marque contenant que des lettres, a verifier si des marques ac nombres existent
        if( field.equals("nom")) {
            if (value.matches("[a-z-A-Z]*")) {
                SystemOutPrintHelper.messageJaune("Champ retourne nom MARQUE OK.");
            } else {
                SystemOutPrintHelper.messageBleu("Champ retourne nom MARQUE mauvais format.");
                console.alert("Le nom de la marque doit etre seulement composé de lettre.");
                return false;
            }

            if(id != null) {
                String nomMarque = value.substring(0,1).toUpperCase() + value.substring(1).toLowerCase();
                MarqueService marqueService = new MarqueService();
                Marque marque = marqueService.getByKey("nom", nomMarque);
                if(marque != null && marque.getId() != id) {
                    SystemOutPrintHelper.messageBleu("Association marque KO car doublon.");
                    console.alert("Ce nom de marque '" +nomMarque  +"' existe déjà.");
                    return false;
                }
                SystemOutPrintHelper.messageJaune("Nom marque unique OK.");
            }
        }
        return true;
    }

}
