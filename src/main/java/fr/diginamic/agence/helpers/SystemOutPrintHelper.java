package fr.diginamic.agence.helpers;

public class SystemOutPrintHelper {

    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_YELLOW = "\u001B[33m";
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_BLUE = "\u001B[34m";

    //Pour les erreurs metiers
    public static void messageJaune(String message) {
        System.out.println(ANSI_YELLOW +"########## " +message +ANSI_RESET);
    }

    //Pour le debugs
    public static void messageBleu(String message) {
        System.out.println(ANSI_BLUE +"########## " +message +ANSI_RESET);
    }

}
