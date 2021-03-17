package fr.diginamic.swing.serviceAgence.Vehicule;

import fr.diginamic.agence.dao.VehiculeDAO;
import fr.diginamic.agence.entity.vehicule.*;
import fr.diginamic.agence.helpers.SystemOutPrintHelper;
import fr.diginamic.agence.service.*;
import fr.diginamic.swing.CsvBuilder;
import fr.diginamic.swing.composants.MenuService;
import fr.diginamic.swing.composants.ui.*;
import fr.diginamic.swing.serviceAgence.EmptyValuesCheck;
import fr.diginamic.swing.serviceAgence.Vehicule.Validator.VehiculeFormValidator;

import java.util.*;
import java.util.stream.Collectors;

/**
 * UI de gestion des vehicules
 * affiche tous les vehicules sous forme de liste
 * Possibilite de supprimer ou modifier des vehicules (seulement si statut est DISPONIBLE)
 */
public class ListeVehiculeServiceSwing extends MenuService {

    protected VehiculeService vehiculeService;
    protected MarqueService marqueService;
    protected TypeVehiculeService typeVehiculeService;
    protected VoitureService voitureService;
    protected CamionService camionService;

    public ListeVehiculeServiceSwing() {
        this.vehiculeService = new VehiculeService(new VehiculeDAO());
        this.marqueService = new MarqueService();
        this.typeVehiculeService = new TypeVehiculeService();
        this.camionService = new CamionService();
        this.voitureService = new VoitureService();
    }

    public void traitement(String typeVehicule) {
        List<Vehicule> listeVehicules = vehiculeService.getAll();
        SystemOutPrintHelper.messageJaune("" + listeVehicules.size());
        console.clear();

        String voituresBG = typeVehicule.equals("voitures") ? "bg-selected" : "";
        String camionsBG = typeVehicule.equals("camions") ? "bg-selected" : "";
        String vehiculesBG = typeVehicule.equals("vehicules") ? "bg-selected" : "";

        console.println("<h1 class='bg-dark-blue' height='30px'><center>Liste des " +typeVehicule +"</center></h1>");
        Integer counter = 0;
        String background;
        String html = "";



        if(listeVehicules.size() == 0) {
            console.print("<h1 class='bg-dark-blue'><center>Aucun " +typeVehicule +" présent</center></h1>");
        } else {
            html += "<table class='table' cellspacing=0> <tr>";
            html += "<td width='250px' height='30px'><a href='getListeCSV()'>Telecharger la liste des vehicules</a></td>";
            html += "<td width='150px' height='30px' class='" +vehiculesBG +"'><a href='traitement(vehicules)'>TOUS LES VEHICULES</a></td>";
            html += "<td width='150px' height='30px' class='" +voituresBG +"'><a href='traitement(voitures)'>VOITURES</a></td>";
            html += "<td width='150px' height='30px' class='" +camionsBG +"'><a href='traitement(camions)'>CAMIONS</a></td>";
            html += "</tr>";
            html += "</table>";

            if(typeVehicule.equals("camions")) {
                listeVehicules.removeIf( v -> v.getNom().equals(Voiture.class.getSimpleName()) );
            } else if(typeVehicule.equals("voitures")) {
                listeVehicules.removeIf( v -> v.getNom().equals(Camion.class.getSimpleName()) );
            }
            html += "<table class='table' cellspacing=0>";
            html += "<tr class='bg-dark-blue'><td>&nbsp;</td><td>&nbsp;</td>";
            html += "<td>Type</td><td>Marque</td>";
            html += "<td>Modele</td>";
            html += "<td>Immatriculation</td>";
            html += "<td>Kilometrage</td>";
            if(!typeVehicule.equals("vehicules")) {
                if(typeVehicule.equals("voitures")) {
                    html += "<td>Places</td>";
                } else {
                    html += "<td>Volume</td>";
                }
            }
            html += "<td>Nb reservations</td>";
            html += "<td>Status</td></tr>";

            for (Vehicule vehicule : listeVehicules) {
                counter++;
                background = counter % 2 == 0 ? "odd" : "bg-white";
                html += "<tr height='80px' class='" + background  +"'>";
                //check si modifiation ou suppression possible
                if(vehicule.getStatut().equals(StatutVehicule.DISPONIBLE)) {
                    html += "<td class='bg-white'><a class='btn-blue' href='modifier(" + vehicule.getId() +")'><img width=25 src='images/pencil-blue-xs.png'></a></td>"; // MODIFIER
                    html += "<td class='bg-white'><a class='btn-red' href='supprimer(" + vehicule.getId() + ")'><img width=25 src='images/trash-red-xs.png'></a></td>"; // SUPPRIMER
                } else {
                    html += "<td class='bg-white'><img width=25 src='images/pencil-grey-xs.png'></td>"; // MODIFIER
                    html += "<td class='bg-white'><img width=25 src='images/trash-grey-xs.png'></td>"; // SUPPRIMER
                }
                html += "<td width='110px'>" + vehicule.getNom() + "</td>"; //TYPE
                html += "<td width='110px'>" + vehicule.getMarque().getNom() + "</td>"; //MARQUE
                html += "<td width='110px'>" + vehicule.getModele() + "</td>"; //MODELE
                html += "<td width='110px'>" + vehicule.getImmatriculation() + "</td>"; //IMMATRICULATION
                html += "<td width='110px'>" + vehicule.getKilometrage() + "</td>"; //KILOMETRAGE
                if(!typeVehicule.equals("vehicules")) {
                    if (typeVehicule.equals("voitures")) {
                        html += "<td width='110px'>" + ((Voiture) vehicule).getNombrePlaces() + "</td>"; //PLACES
                    } else if (typeVehicule.equals("camions")) {
                        html += "<td width='110px'>" + ((Camion) vehicule).getVolumeDisponible() + "</td>"; //VOLUME
                    }
                }
                html += "<td width='110px'>" + vehicule.getReservations().size() + "</td>"; //RESERVATIONS
                html += "<td width='110px'>" + vehicule.getStatut() + "</td>"; //STATUT
                html += "</tr>";
            }
            html += "</table>";
        }

        console.println(html);
    }


    /**
     * Modifie un vehicule a partir de son ID.
     * @param id
     */
    public void modifier(Long id) {
        Vehicule vehicule = (Vehicule) vehiculeService.getById(id);
        Form form = new Form();

        form.addInput(new TextField("Kilometrage:", "kilometrage", "" + vehicule.getKilometrage() ));
        form.addInput(new TextField("Modele:", "modele", vehicule.getModele() ));

        if(vehiculeService.checkIfAlreadyHasReservation( vehicule )) {
            form.addInput(new TextField("Immatriculation:", "immatriculation", vehicule.getImmatriculation(),false));
        } else {
            form.addInput(new TextField("Immatriculation:", "immatriculation", vehicule.getImmatriculation()));
        }

        // Recuperation des marques existantes
        List<Marque> marques = marqueService.getAll();
        List<String> nomMarque = marques.stream().map(m -> {return m.getNom();}).collect(Collectors.toList());
        form.addInput(new ComboBoxStr("Marque:", "marque", nomMarque, vehicule.getMarque().getNom() ));

        // Recuperation des types existants
        List<TypeVehicule> types = typeVehiculeService.getAll();
        List<String> typesList = types.stream().map(m -> {return m.getNom();}).collect(Collectors.toList());
        form.addInput(new ComboBoxStr("Type:", "type", typesList, vehicule.getTypeVehicule().getNom()));

        List<String> listeChecks = new ArrayList<>();
        if(vehicule.getNom().equals(Camion.class.getSimpleName())) {
            form.addInput(new TextField("Volume:", "volume", "" + ((Camion) vehicule).getVolumeDisponible() ));
            listeChecks = Arrays.asList("modele", "immatriculation", "kilometrage", "marque", "volume");
        } else if(vehicule.getNom().equals(Voiture.class.getSimpleName())) {
            form.addInput(new TextField("Places:", "places", "" + ((Voiture) vehicule).getNombrePlaces() ));
            listeChecks = Arrays.asList("modele", "immatriculation", "kilometrage", "marque", "places");
        }

        EmptyValuesCheck checks = new EmptyValuesCheck(listeChecks, new VehiculeFormValidator(), id);

        boolean validation = console.input("Modification d'un(e) " +vehicule.getNom(), form, checks);
        if(validation) {
            vehicule.setImmatriculation(form.getValue("immatriculation"));
            vehicule.setMarque(marqueService.getByKey("nom" ,form.getValue("marque")));
            vehicule.setTypeVehicule(typeVehiculeService.getByKey("nom", form.getValue("type")));
            vehicule.setKilometrage(Long.parseLong(form.getValue("kilometrage")));
            vehicule.setModele(form.getValue("modele"));
            if(vehicule.getNom().equals(Camion.class.getSimpleName())) {
                ((Camion) vehicule).setVolumeDisponible(Integer.parseInt(form.getValue("volume")) );
                camionService.update((Camion) vehicule);
                SystemOutPrintHelper.messageJaune("UPDATE CAMION");
            } else if(vehicule.getNom().equals(Voiture.class.getSimpleName())) {
                ((Voiture) vehicule).setNombrePlaces(Integer.parseInt(form.getValue("places")) );
                voitureService.update((Voiture) vehicule);
                SystemOutPrintHelper.messageJaune("UPDATE VOITURE");
            }
        }
        this.traitement();
    }

    /**
     * Supression d'un vehicule a partir de son ID
     * Peut etre supprimé seulement si il a jamais eu de reservation.
     * @param id
     */
    public void supprimer(Long id) {
        Vehicule vehicule = (Vehicule) vehiculeService.getById(id);
        if(!vehiculeService.checkIfAlreadyHasReservation(vehicule)) {
            boolean result = console.confirm("Suppression du " + vehicule.getNom() + " " +vehicule.getMarque().getNom()  +" " +vehicule.getModele() ,
                    "Confirmez-vous la suppression du vehicule => " + vehicule.getNom() + " .Marque : " +vehicule.getMarque().getNom()  +".Modele : " +vehicule.getModele() );
            if (result) {
                vehiculeService.delete(vehicule);
                traitement();
            }
        } else {
            console.alert("Impossible de supprimer ce vehicule, il a déjà eu des reservations.");
        }
        this.traitement();
    }

    @Override
    public void traitement() {
        this.traitement("vehicules");
    }

    public void getListeCSV() {
        console.alert("Merci d'attendre qq instants le temps de la generation.");
        List<Vehicule> listeVehicule = vehiculeService.getAll();

        List<String> headers = Arrays.asList("id", "Sorte", "Marque", "Type", "Modele", "Immatriculation", "Nb reservations", "Statut");
        List<List<String>> listConteneur = new ArrayList<>();

        for (Vehicule vehicule : listeVehicule) {
            List<String> listeInfos = new ArrayList<>();
            String id = "" +vehicule.getId();
            String nom = vehicule.getNom();
            String type = vehicule.getTypeVehicule().getNom();
            String immatricu = vehicule.getImmatriculation();
            String modele = vehicule.getModele();
            String marque = vehicule.getMarque().getNom();
            String statut = "" + vehicule.getStatut();
            String nbResa = "" + vehicule.getReservations().size();
            listeInfos.add(id);
            listeInfos.add(nom);
            listeInfos.add(marque);
            listeInfos.add(type);
            listeInfos.add(modele);
            listeInfos.add(immatricu);
            listeInfos.add(nbResa);
            listeInfos.add(statut);

            listConteneur.add(listeInfos);
        }

        CsvBuilder.creationFichierCSV("listeVehicules.csv",  ",", "\n", headers, listConteneur);
        this.traitement();
        console.alert("Fichier généré. Il a été uploadé sur internet. Il est disponible a l'adresse suivante =>  https://3.139.215.128/diginamic/listeVehicules.csv");
    }


    /*
    public void ajoutTri(String tri) {
        SystemOutPrintHelper.messageBleu("debut =>" +this.tri + ". " +tri);
        if(tri.equals(this.tri)) {
            this.ordre = false;
        } else {
            this.ordre = true;
        }

        SystemOutPrintHelper.messageBleu("" +this.ordre);
        if(tri.equals("modele")) {
            this.listeVehicules = this.listeVehicules.stream().sorted((object1, object2) -> object1.getModele().compareTo(object2.getModele())).collect(Collectors.toList());
        } else if(tri.equals("immatriculation")) {
            this.listeVehicules = this.listeVehicules.stream().sorted((object1, object2) -> object2.getImmatriculation().compareTo(object1.getImmatriculation())).collect(Collectors.toList());
        } else if(tri.equals("kilometrage") && this.ordre) {
            this.listeVehicules = this.listeVehicules.stream().sorted((object1, object2) -> object1.getKilometrage().compareTo(object2.getKilometrage())).collect(Collectors.toList());
        } else if(tri.equals("kilometrage") && !this.ordre) {
            this.listeVehicules = this.listeVehicules.stream().sorted((object1, object2) -> object2.getKilometrage().compareTo(object1.getKilometrage())).collect(Collectors.toList());
        }
        console.clear();
        this.tri = tri;
        SystemOutPrintHelper.messageBleu("Fin =>" +this.tri +" ." + tri);
        traitement();
    }
    */

}
