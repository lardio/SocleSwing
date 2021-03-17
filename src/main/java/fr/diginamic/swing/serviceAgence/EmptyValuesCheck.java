package fr.diginamic.swing.serviceAgence;

import fr.diginamic.agence.helpers.SystemOutPrintHelper;
import fr.diginamic.swing.composants.ui.Form;
import fr.diginamic.swing.composants.validator.FormValidator;

import java.util.ArrayList;
import java.util.List;

/**
 * Verifie la conformité metier des champs renseignés dans le formulaire
 * Prend en premier argument une liste de String. Chaque valeur correspond a un champ du formulaire. Au minimum chaque champ renseigné doit etre non vide.
 * Prend en deuxieme argument une classe enfante de AgenceFormValidator. Cette classe fait des verifications metiers sur les champs du formulaire necessaires.
 * Prend en troisieme argument un Integer. Entier positif si on souhaite modifier un objet. Null en cas de creation.
 * @param <T>
 *
 * @author sylvain
 */
public class EmptyValuesCheck<T extends AgenceFormValidator> extends FormValidator {

    protected List<String> listeCheck = new ArrayList<>();
    protected T formValidator;
    protected Long idToCheck;

    public EmptyValuesCheck (List<String> liste, T formValidator, Long id) {
        this.formValidator = formValidator;
        this.listeCheck = liste;
        this.idToCheck = id;
    }

    /**
     * Verification de la conformité metier des champs renseignés.
     * @param form formulaire
     * @return
     */
    @Override
    public boolean validate(Form form) {
        //check si aucun champ est vide.
        for (String field : listeCheck) {
            SystemOutPrintHelper.messageBleu(field);
            String fieldToCheck = form.getValue(field);
            if(fieldToCheck.trim().isEmpty()) {
                console.alert("Le champ " +field +" est obligatoire");
                return false;
            }
            SystemOutPrintHelper.messageJaune(field);
            if (!formValidator.checkValueFromForm(field, fieldToCheck.trim(), idToCheck, console)) return false;
        }
        return true;
    }


}
