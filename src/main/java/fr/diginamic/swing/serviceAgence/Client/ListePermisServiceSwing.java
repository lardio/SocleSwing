package fr.diginamic.swing.serviceAgence.Client;

import fr.diginamic.agence.entity.client.TypePermis;
import fr.diginamic.agence.entity.vehicule.Marque;
import fr.diginamic.agence.service.TypePermisService;
import fr.diginamic.swing.composants.MenuService;
import fr.diginamic.swing.composants.ui.Form;
import fr.diginamic.swing.composants.ui.TextField;
import fr.diginamic.swing.serviceAgence.Client.Validator.PermisFormValidator;
import fr.diginamic.swing.serviceAgence.EmptyValuesCheck;

import java.util.Arrays;
import java.util.List;

public class ListePermisServiceSwing extends MenuService {

    protected TypePermisService typePermisService;

    public ListePermisServiceSwing(){
        this.typePermisService = new TypePermisService();
    }

    @Override
    public void traitement() {
        console.clear();
        List<TypePermis> typePermis = typePermisService.getAll();

        console.println("<h1 class='bg-dark-blue'><center>Ajout d'un permis</center></h1>");

        String html = "<table class='table' cellspacing=0>";
        html += "<tr><td width='150px' height='30px'><a href='ajouter()'><img width=25 src='images/plus-blue.png'></a></td><td width='150px' height='30px'>Permis</td></tr>";
        html += "</table>";

        html += "<br/>";
        html += "<table class='table' cellspacing=0> <tr class='bg-dark-blue'><td>&nbsp;</td><td>&nbsp;</td><td>Nom</td><td>Nb clients</td></tr>";
        Integer counter = 0;
        String background;

        for (TypePermis typePermisUnite : typePermis) {
            counter++;
            background = counter % 2 == 0 ? "odd" : "bg-white";

            html += "<tr class='" + background  +"'>";
            if(typePermisUnite.getListePermis().size() == 0) {
                html += "<td class='bg-white'><a class='btn-blue' href='modifier(" + typePermisUnite.getId() +")'><img width=25 src='images/pencil-blue-xs.png'></a></td>"; // MODIFIER
                html += "<td class='bg-white'><a class='btn-red' href='supprimer(" + typePermisUnite.getId() + ")'><img width=25 src='images/trash-red-xs.png'></a></td>"; // SUPPRIMER
            } else {
                html += "<td class='bg-white'><img width=25 src='images/pencil-grey-xs.png'></td>"; // MODIFIER
                html += "<td class='bg-white'><img width=25 src='images/trash-grey-xs.png'></td>"; // SUPPRIMER
            }
            html += "<td width='150px'>" + typePermisUnite.getNom() + "</td>";
            html += "<td width='150px'>" + typePermisUnite.getListePermis().size() + "</td>";
            html += "</tr>";
        }
        html += "</table>";
        console.println(html);
    }

    public void ajouter() {
        Form form = new Form();

        form.addInput(new TextField("Nom du type:", "nomPermis"));
        List<String> listeChecks = Arrays.asList("nomPermis");
        EmptyValuesCheck checks = new EmptyValuesCheck(listeChecks, new PermisFormValidator(), null);

        boolean validation = console.input("Ajout d'un type de permis", form, checks);
        if (validation) {
            TypePermis permis = new TypePermis();
            permis.setNom(form.getValue("nomPermis"));
            typePermisService.create(permis);
        }
        traitement();

    }
}
