package fr.diginamic.swing.serviceAgence.Vehicule;

import fr.diginamic.agence.dao.VehiculeDAO;
import fr.diginamic.agence.entity.vehicule.TypeVehicule;
import fr.diginamic.agence.helpers.SystemOutPrintHelper;
import fr.diginamic.agence.service.TypeVehiculeService;
import fr.diginamic.agence.service.VehiculeService;
import fr.diginamic.swing.composants.MenuService;
import fr.diginamic.swing.composants.ui.Form;
import fr.diginamic.swing.composants.ui.TextField;
import fr.diginamic.swing.serviceAgence.EmptyValuesCheck;
import fr.diginamic.swing.serviceAgence.Vehicule.Validator.TypeFormValidator;

import java.util.Arrays;
import java.util.List;

public class TypeVehiculeServiceSwing extends MenuService {
    protected VehiculeService vehiculeService;
    protected TypeVehiculeService typeVehiculeService;

    public TypeVehiculeServiceSwing() {
        this.vehiculeService = new VehiculeService(new VehiculeDAO());
        this.typeVehiculeService = new TypeVehiculeService();
    }

    @Override
    public void traitement() {
        List<TypeVehicule> listeTypes = typeVehiculeService.getAll();
        console.clear();
        console.println("<h1 class='bg-dark-blue'><center>Liste des types de vehicule</center></h1>");
        String html = "";

        html += "<table class='table' cellspacing=0 >";
        html += "<tr class='bg-dark-blue'>";
        html += "<td width='150px' height='30px'><a href='ajouter()'><img width=25 src='images/plus-blue.png'></a></td>";
        html += "<td width='150px' height='30px'>AJOUTER UN TYPE</td>";
        html += "</tr>";
        html += "</table>";

        html += "<br/>";
        html += "<table class='table' cellspacing=0> <tr class='bg-dark-blue'><td>&nbsp;</td><td>&nbsp;</td><td>Nom</td><td>Tarif journalier</td><td>Caution</td><td>Nb Vehicules</td></tr>";
        Integer counter = 0;
        String background;

        for (TypeVehicule type : listeTypes) {
            counter++;
            background = counter % 2 == 0 ? "odd" : "bg-white";

            html += "<tr class='" + background  +"'>";
            if(type.getVehicules().size() == 0) {
                html += "<td class='bg-white'><a class='btn-blue' href='modifier(" + type.getId() +")'><img width=25 src='images/pencil-blue-xs.png'></a></td>"; // MODIFIER
                html += "<td class='bg-white'><a class='btn-red' href='supprimer(" + type.getId() + ")'><img width=25 src='images/trash-red-xs.png'></a></td>"; // SUPPRIMER
            } else {
                html += "<td class='bg-white'><img width=25 src='images/pencil-grey-xs.png'></td>"; // MODIFIER
                html += "<td class='bg-white'><img width=25 src='images/trash-grey-xs.png'></td>"; // SUPPRIMER
            }
            html += "<td width='150px'>" + type.getNom() + "</td>";
            html += "<td width='150px'>" + type.getTarif() + "</td>";
            html += "<td width='150px'>" + type.getCaution() + "</td>";
            html += "<td width='150px'>" + type.getVehicules().size() + "</td>";
            html += "</tr>";
        }
        html += "</table>";
        console.println(html);
    }

    public void modifier(Long id) {
        TypeVehicule typeVehiculeBefore = typeVehiculeService.getById(id);
        Form form = new Form();
        form.addInput(new TextField("Nom:", "nom", "" + typeVehiculeBefore.getNom()));
        form.addInput(new TextField("Tarif journalier:", "tarif" + "" +typeVehiculeBefore.getTarif()));
        form.addInput(new TextField("Caution:", "caution" + "" +typeVehiculeBefore.getCaution()));

        List<String> listeChecks = Arrays.asList("nom", "tarif", "caution");
        EmptyValuesCheck checks = new EmptyValuesCheck(listeChecks, new TypeFormValidator(), id);

        boolean validation = console.input("Modification d'un type", form, checks);
        if (validation) {
            TypeVehicule type = typeVehiculeService.getById(id);
            String nomType = form.getValue("nom");
            nomType = nomType.substring(0,1).toUpperCase() + nomType.substring(1).toLowerCase();
            type.setNom(nomType);
            type.setCaution(Double.parseDouble(form.getValue("tarif")) );
            type.setTarif(Double.parseDouble(form.getValue("caution")) );
            typeVehiculeService.update(type);
        }
        traitement();
    }

    public void supprimer(Long id) {
        TypeVehicule type = typeVehiculeService.getById(id);
        if (type.getVehicules().size() == 0) {
            SystemOutPrintHelper.messageJaune("check supression type OK");
            boolean result = console.confirm("Suppression du type " + type.getNom(), "Confirmer la suppression du type " + type.getNom());
            if (result) {
                typeVehiculeService.delete(type);
                traitement();
                return;
            }
        }
        SystemOutPrintHelper.messageJaune("Envoi message supression type KO");
        this.traitement();
        console.alert("Impossible de supprimer le type car des vehicules avec celui-ci existent.");
    }

    public void ajouter() {
        Form form = new Form();
        form.addInput(new TextField("Nom:", "nom"));
        form.addInput(new TextField("Tarif journalier:", "tarif"));
        form.addInput(new TextField("Caution:", "caution"));

        List<String> listeChecks = Arrays.asList("nom", "tarif", "caution");
        EmptyValuesCheck checks = new EmptyValuesCheck(listeChecks, new TypeFormValidator(), null);

        boolean validation = console.input("Ajout d'un type" , form, checks);
        if (validation) {
            String nomType = form.getValue("nom");
            nomType = nomType.substring(0,1).toUpperCase() + nomType.substring(1).toLowerCase();
            TypeVehicule type = new TypeVehicule();
            type.setNom(nomType);
            type.setCaution(Double.parseDouble(form.getValue("caution")) );
            type.setTarif(Double.parseDouble(form.getValue("tarif")) );
            typeVehiculeService.create(type);
        }
        this.traitement();
    }
}