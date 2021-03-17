package fr.diginamic.swing.serviceAgence.Vehicule;

import fr.diginamic.agence.entity.vehicule.*;
import fr.diginamic.agence.service.*;
import fr.diginamic.swing.composants.MenuService;
import fr.diginamic.swing.composants.ui.ComboBoxStr;
import fr.diginamic.swing.composants.ui.Form;
import fr.diginamic.swing.composants.ui.TextField;
import fr.diginamic.swing.serviceAgence.EmptyValuesCheck;
import fr.diginamic.swing.serviceAgence.Vehicule.Validator.VehiculeFormValidator;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class AddVehiculeServiceSwing extends MenuService {

    protected MarqueService marqueService;
    protected TypeVehiculeService typeVehiculeService;
    protected VoitureService voitureService;
    protected CamionService camionService;

    public AddVehiculeServiceSwing () {
        this.marqueService = new MarqueService();
        this.typeVehiculeService = new TypeVehiculeService();
        this.voitureService = new VoitureService();
        this.camionService = new CamionService();
    }

    @Override
    public void traitement() {
        console.clear();
        console.println("<h1 class='bg-dark-blue'><center>Ajout vehicule</center></h1>");

        String html = "<table class='table' cellspacing=0>";
        html += "<tr><td width='150px' height='30px'><a href='ajouter(" + Voiture.class.getSimpleName() +")'><img width=25 src='images/plus-blue.png'></a></td><td width='150px' height='30px'>Voiture</td></tr>";
        html += "<tr><td width='150px' height='30px'><a href='ajouter(" + Camion.class.getSimpleName() +")'><img width=25 src='images/plus-blue.png'></a></td><td width='150px' height='30px'>Camion</td></tr>";
        html += "</table>";
        console.print(html);
    }


    public void ajouter(String sorteVehicule) {
        Form form = new Form();

        List<Marque> marques = marqueService.getAll();
        if(marques.size() < 1) {
            traitement();
            console.alert("Aucune marque est enregistrée, merci de créer au minimum une marque avant de créer un vehicule.");
            return;
        }
        List<String> nomMarque = marques.stream().map(m -> {return m.getNom();}).collect(Collectors.toList());
        form.addInput(new ComboBoxStr("Marque:", "marque", nomMarque));

        List<TypeVehicule> types = typeVehiculeService.getAll();
        if(types.size() < 1) {
            traitement();
            console.alert("Aucun type de vehicule est enregistré, merci de créer au minimum un type avant de créer un vehicule.");
            return;
        }
        List<String> typesList = types.stream().map(m -> {return m.getNom();}).collect(Collectors.toList());
        form.addInput(new ComboBoxStr("Type:", "type", typesList));

        form.addInput(new TextField("Modele:", "modele"));
        form.addInput(new TextField("Immatriculation:", "immatriculation"));
        form.addInput(new TextField("Kilometrage:", "kilometrage"));
        String nomChampSpe = sorteVehicule.equals(Voiture.class.getSimpleName()) ? "places" : "volume";
        form.addInput(new TextField(sorteVehicule.equals(Voiture.class.getSimpleName()) ? "Nombre de places:" : "Volume:", nomChampSpe));

        List<String> listeChecks = Arrays.asList("immatriculation", "kilometrage", "modele", nomChampSpe);
        EmptyValuesCheck checks = new EmptyValuesCheck(listeChecks, new VehiculeFormValidator(), null);

        boolean validation = console.input("Ajout d'un(e) " + sorteVehicule, form, checks);
        if(validation) {
            if(sorteVehicule.equals(Voiture.class.getSimpleName())) {
                Voiture voiture = new Voiture();
                voiture.setNombrePlaces(Integer.parseInt(form.getValue("places")));
                voiture.setImmatriculation(form.getValue("immatriculation"));
                voiture.setKilometrage(Long.parseLong(form.getValue("kilometrage")));
                voiture.setModele(form.getValue("modele"));
                voiture.setMarque(marqueService.getByKey("nom" ,form.getValue("marque")));
                voiture.setTypeVehicule(typeVehiculeService.getByKey("nom", form.getValue("type")));
                voitureService.create(voiture);
            } else {
                Camion camion = new Camion();
                camion.setVolumeDisponible(Integer.parseInt(form.getValue("volume")));
                camion.setImmatriculation(form.getValue("immatriculation"));
                camion.setKilometrage(Long.parseLong(form.getValue("kilometrage")));
                camion.setModele(form.getValue("modele"));
                camion.setMarque(marqueService.getByKey("nom", form.getValue("marque")));
                camion.setTypeVehicule(typeVehiculeService.getByKey("nom", form.getValue("type")));
                camionService.create(camion);
            }
        }
        this.traitement();
    }

}
