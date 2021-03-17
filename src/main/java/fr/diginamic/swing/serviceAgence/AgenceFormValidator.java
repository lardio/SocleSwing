package fr.diginamic.swing.serviceAgence;

import fr.diginamic.swing.composants.Console;

/**
 * Verifie la conformité metier des champs renseignés dans le formulaire
 * Prend en premier argument une liste de String. Chaque valeur correspond a un champ du formulaire. Au minimum chaque champ renseigné doit etre non vide.
 * Prend en deuxieme argument une classe enfante de AgenceFormValidator. Cette classe fait des verifications metiers sur les champs du formulaire necessaires.
 * Prend en troisieme argument un Integer. Entier positif si on souhaite modifier un objet. Null en cas de creation.
 * @param <T>
 *
 * @author sylvain
 */
public abstract class AgenceFormValidator {

    public abstract boolean checkValueFromForm(String field, String value, Long id, Console console);
}
