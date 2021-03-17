package fr.diginamic.agence;

import fr.diginamic.agence.Exception.WrongImmatriculation;
import fr.diginamic.agence.dao.VehiculeDAO;
import fr.diginamic.agence.helpers.SystemOutPrintHelper;
import fr.diginamic.agence.service.*;
import fr.diginamic.swing.CsvBuilder;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.*;

public class Test {

    private static final String DELIMITER = ",";
    private static final String SEPARATOR = "\n";

    //En-tête de fichier
    private static final String HEADER = "Title,Author,Year";

    private static ChannelSftp setupJsch() throws JSchException {
        String username = "sylvain";
        String password = "sylvain";
        String host = "3.139.215.128";
        JSch jsch = new JSch();
        Session jschSession = jsch.getSession(username, host);
        java.util.Properties config = new java.util.Properties();
        config.put("StrictHostKeyChecking", "no");
        jschSession.setConfig(config);
        jschSession.setPassword(password);
        jschSession.connect();
        return (ChannelSftp) jschSession.openChannel("sftp");
    }

    public static boolean uploadSftpFromInputStream(InputStream localFile, String sftpFile) {
        ChannelSftp channelSftp = null;
        try {
            channelSftp = setupJsch();
        } catch (JSchException e) {
            SystemOutPrintHelper.messageJaune(e.getMessage());
        }
        try {
            channelSftp.connect();
        } catch (JSchException e) {
            // throw the exception
        }
        try{
            channelSftp.put(localFile, sftpFile);
            System.out.println("Upload Complete");
        } catch (SftpException e) {
            // throw the exception
        }
        channelSftp.exit();
        return true;
    }


    public static void main(String... args) throws WrongImmatriculation, IOException {
        // <table><tr><td>Texte</td><td><img src=...></td></tr></table>

        EntityManager em = EntityManagerGestion.recuperationEntityManager();
        EntityTransaction tx = em.getTransaction();

        VoitureService voitureService = new VoitureService();
        MarqueService marqueService = new MarqueService();
        ReservationService reservationService = new ReservationService();
        MaintenanceService maintenanceService = new MaintenanceService();
        VehiculeService vehiculeService = new VehiculeService(new VehiculeDAO());
        TypeVehiculeService typeVehiculeService = new TypeVehiculeService();
        AdresseService adresseService = new AdresseService();
        ClientService clientService = new ClientService();
        FactureService factureService = new FactureService();



        Path currentRelativePath = Paths.get("");
        String s = currentRelativePath.toAbsolutePath().toString();
        System.out.println("Current relative path is: " + s);

        List<List<String>> test = new ArrayList<>();
        List<String> b = new ArrayList<>();
        List<String> c = new ArrayList<>();
        b.add("Sylvain");
        b.add("LARDIèRE");
        c.add("kAROLINA");
        c.add("rapala");
        test.add(b);
        test.add(c);
        List<String> headersFichier = Arrays.asList("nom", "prenom");

        CsvBuilder.creationFichierCSV("test.csv", ",", "\n", headersFichier, test);
        //pourExoLocation



    }

}