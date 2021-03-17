package fr.diginamic.agence.Exception;

public class WrongImmatriculation extends Exception {

    protected String errorMessage = "Attention le format de l'immatriculation est mauvais";

    public WrongImmatriculation() {
        System.out.println(errorMessage);
    }

}
