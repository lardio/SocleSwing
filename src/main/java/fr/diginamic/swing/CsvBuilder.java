package fr.diginamic.swing;

import fr.diginamic.agence.Test;
import fr.diginamic.agence.helpers.SystemOutPrintHelper;

import java.io.*;
import java.util.Iterator;
import java.util.List;

/**
 * Permet de creer des fichiers csv a partir de liste.
 * @author Sylvain
 */
public class CsvBuilder {

    /**
     * Créé un fichier CSV.
     * @param nomFichier fichier qui sera créé.
     * @param delimiter normalement un saut de ligne pr fichier csv. Sera appelé a chaque fin de ligne
     * @param separator delimiter pour le fichier CSV
     * @param headers List de String qui constitueront les en-tetes
     * @param donnees List des données. Une liste de liste de String. La premiere liste sera les lignes, la secondes les données en colonnes.
     *
     * //A ameliorer avec des tests pour gestion décriture du fichier, test sur delimiter/separateur, caracteres speciaux etc.
     */
    public static void creationFichierCSV(String nomFichier, String delimiter, String separator, List<String> headers, List<List<String>> donnees) {
        File fichier = new File(nomFichier);
        fichier.setReadable(true);
        fichier.setWritable(true);

        try {
            FileWriter fichierCSV = new FileWriter(fichier);

            String contenuHeader = String.join(",", headers);
            fichierCSV.append(contenuHeader);
            fichierCSV.append(separator);

            Iterator lignes = donnees.iterator();
            while(lignes.hasNext()) {
                SystemOutPrintHelper.messageBleu("Lignes suivante");
                List<String> colonnes = (List) lignes.next();
                for(int i = 0; i < colonnes.size(); i++) {
                    fichierCSV.append(colonnes.get(i));
                    if(i + 1 == colonnes.size()) {
                        fichierCSV.append(separator);
                    } else {
                        fichierCSV.append(delimiter);
                    }
                }
            }
            fichierCSV.close();
            InputStream targetStream = new FileInputStream(fichier);
            SystemOutPrintHelper.messageJaune("Envoi fichier " +nomFichier);
            Test.uploadSftpFromInputStream(targetStream, "/opt/bitnami/apache/htdocs/diginamic/" +nomFichier);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
