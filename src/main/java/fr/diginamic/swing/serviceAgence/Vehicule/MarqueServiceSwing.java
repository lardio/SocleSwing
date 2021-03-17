package fr.diginamic.swing.serviceAgence.Vehicule;

import fr.diginamic.agence.dao.VehiculeDAO;
import fr.diginamic.agence.entity.vehicule.Marque;
import fr.diginamic.agence.entity.vehicule.StatutVehicule;
import fr.diginamic.agence.helpers.SystemOutPrintHelper;
import fr.diginamic.agence.service.MarqueService;
import fr.diginamic.agence.service.TypeVehiculeService;
import fr.diginamic.agence.service.VehiculeService;
import fr.diginamic.swing.composants.MenuService;
import fr.diginamic.swing.composants.ui.Form;
import fr.diginamic.swing.composants.ui.TextField;
import fr.diginamic.swing.serviceAgence.EmptyValuesCheck;
import fr.diginamic.swing.serviceAgence.Vehicule.Validator.MarqueFormValidator;

import java.util.Arrays;
import java.util.List;

public class MarqueServiceSwing extends MenuService {
    protected VehiculeService vehiculeService;
    protected MarqueService marqueService;
    protected TypeVehiculeService typeVehiculeService;

    public MarqueServiceSwing() {
        this.vehiculeService = new VehiculeService(new VehiculeDAO());
        this.marqueService = new MarqueService();
        this.typeVehiculeService = new TypeVehiculeService();
    }

    @Override
    public void traitement() {
        List<Marque> listeMarques = marqueService.getAll();
        console.clear();
        console.println("<h1 class='bg-dark-blue'><center>Liste des marques</center></h1>");
        String html = "";

        html += "<table class='table' cellspacing=0 >";
        html += "<tr class='bg-dark-blue'>";
        html += "<td width='150px' height='30px'><a href='ajouter()'><img width=25 src='images/plus-blue.png'></a></td>";
        html += "<td width='150px' height='30px'>AJOUTER UNE MARQUE</td>";
        html += "</tr>";
        html += "</table>";

        html += "<br/>";
        html += "<table class='table' cellspacing=0> <tr class='bg-dark-blue'><td>&nbsp;</td><td>&nbsp;</td><td>Nom</td><td>Nb Vehicules</td></tr>";
        Integer counter = 0;
        String background;

        for (Marque marque : listeMarques) {
            counter++;
            background = counter % 2 == 0 ? "odd" : "bg-white";

            html += "<tr class='" + background  +"'>";
            if(marque.getVehicules().size() == 0) {
                html += "<td class='bg-white'><a class='btn-blue' href='modifier(" + marque.getId() +")'><img width=25 src='images/pencil-blue-xs.png'></a></td>"; // MODIFIER
                html += "<td class='bg-white'><a class='btn-red' href='supprimer(" + marque.getId() + ")'><img width=25 src='images/trash-red-xs.png'></a></td>"; // SUPPRIMER
            } else {
                html += "<td class='bg-white'><img width=25 src='images/pencil-grey-xs.png'></td>"; // MODIFIER
                html += "<td class='bg-white'><img width=25 src='images/trash-grey-xs.png'></td>"; // SUPPRIMER
            }
            html += "<td width='150px'>" + marque.getNom() + "</td>";
            html += "<td width='150px'>" + marque.getVehicules().size() + "</td>";
            html += "</tr>";
        }
        html += "</table>";
        console.println(html);
    }

    public void modifier(Long id) {
        Form form = new Form();
        form.addInput(new TextField("Nom:", "nom"));

        List<String> listeChecks = Arrays.asList("nom");
        EmptyValuesCheck checks = new EmptyValuesCheck(listeChecks, new MarqueFormValidator(), id);

        boolean validation = console.input("Modification d'une marque", form, checks);
        if (validation) {
                Marque marque = marqueService.getById(id);
                String nomMarque = form.getValue("nom");
                nomMarque = nomMarque.substring(0,1).toUpperCase() + nomMarque.substring(1).toLowerCase();
                marque.setNom(nomMarque);
                marqueService.update(marque);
        }
        traitement();
    }

    public void supprimer(Long id) {
        Marque marque = marqueService.getById(id);
        if (marque.getVehicules().size() == 0) {
            SystemOutPrintHelper.messageJaune("check supression marque OK");
            boolean result = console.confirm("Suppression de la marque " + marque.getNom(), "Confirmer la suppression de la marque " + marque.getNom());
            if (result) {
                marqueService.delete(marque);
                traitement();
                return;
            }
        }
        SystemOutPrintHelper.messageJaune("Envoi message supression marque KO");
        this.traitement();
        console.alert("Impossible de supprimer la marque car des vehicules avec celle-ci existent.");
    }

    public void ajouter() {
        Form form = new Form();
        form.addInput(new TextField("Nom:", "nom"));

        List<String> listeChecks = Arrays.asList("nom");
        EmptyValuesCheck checks = new EmptyValuesCheck(listeChecks, new MarqueFormValidator(), null);

        boolean validation = console.input("Ajout d'une marque" , form, checks);
        if (validation) {
            String nomMarque = form.getValue("nom");
            nomMarque = nomMarque.substring(0,1).toUpperCase() + nomMarque.substring(1).toLowerCase();
            Marque marque = new Marque();
            marque.setNom(nomMarque);
            marqueService.create(marque);
        }
        this.traitement();
    }
}