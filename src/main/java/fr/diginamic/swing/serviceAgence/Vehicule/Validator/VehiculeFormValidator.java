package fr.diginamic.swing.serviceAgence.Vehicule.Validator;

import fr.diginamic.agence.dao.VehiculeDAO;
import fr.diginamic.agence.entity.vehicule.Marque;
import fr.diginamic.agence.entity.vehicule.TypeVehicule;
import fr.diginamic.agence.entity.vehicule.Vehicule;
import fr.diginamic.agence.helpers.SystemOutPrintHelper;
import fr.diginamic.agence.service.MarqueService;
import fr.diginamic.agence.service.TypeVehiculeService;
import fr.diginamic.agence.service.VehiculeService;
import fr.diginamic.swing.composants.Console;
import fr.diginamic.swing.serviceAgence.AgenceFormValidator;

/**
 * Classe de validation metier des entrées dans ui.
 * Spécifique aux validations des Vehicules
 * @see Vehicule
 */
public class VehiculeFormValidator extends AgenceFormValidator {

    /**
     * Verifie si les données en entrés pour un vehicule match avec les conditions metiers.
     * @param field nom du champ du formulaire a verifier
     * @param value valeur du champ du formulaire a verifier
     * @param id id du vehicule a verifier. Null si creation.
     * @param console pour afficher un message d'alerte si une donnée ne remplie pas les conditions métiers.
     * @return true si tout est OK.
     */
    public boolean checkValueFromForm(String field, String value, Long id, Console console) {
        if( field.equals("kilometrage")) {
            if (value.matches("[0-9]*")) {
                SystemOutPrintHelper.messageJaune("Champ retourne kilometre OK.");
                return true;
            } else {
                SystemOutPrintHelper.messageBleu("Champ retourne kilometre mauvais format.");
                console.alert("Le champ kilometre doit etre de valeur numerique.");
                return false;
            }
        }

        if( field.equals("marque") ) {
            MarqueService marqueService = new MarqueService();
            Marque marque = marqueService.getByKey("nom", value);
            if(marque != null) {
                SystemOutPrintHelper.messageJaune("Champ retourné marque OK.");
                return true;
            } else {
                SystemOutPrintHelper.messageBleu("Champ retourne marque inexistant dans la BDD.");
                console.alert("Le champ marque doit correspondre a une marque existante. Si elle est pas présente, merci de créer d'abord la marque voulue.");
                return false;
            }
        }

        if( field.equals("type") ) {
            TypeVehiculeService typeVehiculeService = new TypeVehiculeService();
            TypeVehicule typeV = typeVehiculeService.getByKey("nom", value);
            if(typeV != null) {
                SystemOutPrintHelper.messageJaune("Champ retourné type vehicule OK.");
                return true;
            } else {
                SystemOutPrintHelper.messageBleu("Champ retourne type vehicule inexistant dans la BDD.");
                console.alert("Le champ type vehicule doit correspondre a un type existant. Si il n'est pas présent, merci de créer d'abord le type voulu.");
                return false;
            }
        }

        if( field.equals("immatriculation") ){
            if(value.matches("[a-zA-Z_0-9]{2}-[a-zA-Z_0-9]{3}-[a-zA-Z_0-9]{2}$")) {
                SystemOutPrintHelper.messageJaune("Champ retourné type vehicule OK.");
            } else {
                SystemOutPrintHelper.messageBleu("Immatriculation mauvais format.");
                console.alert("L'immatriculation donnée est au mauvais format.");
                return false;
            }
            VehiculeService vehiculeService = new VehiculeService(new VehiculeDAO());
            Vehicule vehicule = (Vehicule) vehiculeService.getByKey("immatriculation", value);
            if(vehicule != null && vehicule.getId() != id) {
                SystemOutPrintHelper.messageBleu("Association immatriculation KO car doublon.");
                console.alert("Cette immatriculation est déjà associée a un autre véhicule.");
                return false;
            }
            SystemOutPrintHelper.messageJaune("Immatriculation unique OK.");
        }

        if( field.equals("places") ){
            if(value.matches("[0-9]*")) {
                SystemOutPrintHelper.messageJaune("Champ retourné type place OK.");
            } else {
                SystemOutPrintHelper.messageBleu("Nombre de place au mauvait format.");
                console.alert("Le nombre de places est au mauvais format.");
                return false;
            }
        }

        if( field.equals("volume") ){
            if(value.matches("[0-9]*")) {
                SystemOutPrintHelper.messageJaune("Champ retourné volume OK.");
            } else {
                SystemOutPrintHelper.messageBleu("Volume au mauvait format.");
                console.alert("Le volume est au mauvais format.");
                return false;
            }
        }

        return true;
    }

}

