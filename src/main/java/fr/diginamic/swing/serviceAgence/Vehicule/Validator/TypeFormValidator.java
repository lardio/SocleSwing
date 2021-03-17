package fr.diginamic.swing.serviceAgence.Vehicule.Validator;

import fr.diginamic.agence.entity.vehicule.TypeVehicule;
import fr.diginamic.agence.helpers.SystemOutPrintHelper;
import fr.diginamic.agence.service.TypeVehiculeService;
import fr.diginamic.swing.composants.Console;
import fr.diginamic.swing.serviceAgence.AgenceFormValidator;

public class TypeFormValidator extends AgenceFormValidator {

    @Override
    public boolean checkValueFromForm(String field, String value, Long id, Console console) {
        //Type contenant que des lettres, a changer si besoin
        if( field.equals("nom")) {
            if (value.matches("[a-z-A-Z]*")) {
                SystemOutPrintHelper.messageJaune("Champ retourne nom TYPE OK.");
            } else {
                SystemOutPrintHelper.messageBleu("Champ retourne nom TYPE mauvais format.");
                console.alert("Le nom du type doit etre seulement composé de lettre.");
                return false;
            }

            if(id != null) {
                String nomType = value.substring(0,1).toUpperCase() + value.substring(1).toLowerCase();
                TypeVehiculeService typeVehiculeService = new TypeVehiculeService();
                TypeVehicule typeVehicule = typeVehiculeService.getByKey("nom", nomType);
                if(typeVehicule != null && typeVehicule.getId() != id) {
                    SystemOutPrintHelper.messageBleu("Association TYPE KO car doublon.");
                    console.alert("Ce nom de type '" +nomType  +"' existe déjà.");
                    return false;
                }
                SystemOutPrintHelper.messageJaune("Nom type unique OK.");
            }
        }

        if( field.equals("tarif") || field.equals("caution")) {
            if (value.matches("[0-9]*(.[0-9]{1,2})?")) {
                SystemOutPrintHelper.messageJaune("Champ retourne tarif ou caution OK.");
            } else {
                SystemOutPrintHelper.messageBleu("Champ retourne tarif ou caution au mauvais format.");
                console.alert("Le format du tarif ou de la caution est invalide.");
                return false;
            }
        }
        return true;
    }

}
