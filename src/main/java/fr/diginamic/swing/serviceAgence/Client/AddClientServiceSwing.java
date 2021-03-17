package fr.diginamic.swing.serviceAgence.Client;

import fr.diginamic.agence.entity.client.Adresse;
import fr.diginamic.agence.entity.client.Client;
import fr.diginamic.agence.entity.client.Permis;
import fr.diginamic.agence.entity.client.TypePermis;
import fr.diginamic.agence.helpers.DateHelper;
import fr.diginamic.agence.helpers.SystemOutPrintHelper;
import fr.diginamic.agence.service.AdresseService;
import fr.diginamic.agence.service.ClientService;
import fr.diginamic.agence.service.PermisService;
import fr.diginamic.agence.service.TypePermisService;
import fr.diginamic.swing.CsvBuilder;
import fr.diginamic.swing.composants.MenuService;
import fr.diginamic.swing.composants.ui.ComboBoxStr;
import fr.diginamic.swing.composants.ui.Form;
import fr.diginamic.swing.composants.ui.TextField;
import fr.diginamic.swing.serviceAgence.Client.Validator.ClientFormValidator;
import fr.diginamic.swing.serviceAgence.EmptyValuesCheck;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class AddClientServiceSwing extends MenuService {

    protected ClientService clientService;
    protected TypePermisService typePermisService;
    protected PermisService permisService;
    protected AdresseService adresseService;

    public AddClientServiceSwing() {
        this.typePermisService = new TypePermisService();
        this.clientService = new ClientService();
        this.permisService = new PermisService();
        this.adresseService = new AdresseService();
    }

    @Override
    public void traitement() {
        console.clear();
        List<Client> listeClient = clientService.getAll();

        console.println("<h1 class='bg-dark-blue'><center>Ajout d'un client</center></h1>");

        String html = "<table class='table' cellspacing=0>";
        html += "<tr><td width='150px' height='30px'><a href='ajouter()'><img width=25 src='images/plus-blue.png'></a></td><td width='250px' height='30px'>Ajouter un client</td></tr>";
        html += "<tr><td width='150px' height='30px'><a href='getListeCSV()'><img width=25 src='images/file-blue-xs.png'></a></td><td width='250px' height='30px'>Telecharger la liste des clients</td></tr>";
        html += "</table>";
        html += "</tr>";

        html += "<br/>";
        html += "<table class='table' cellspacing=0> <tr class='bg-dark-blue'><td>&nbsp;</td><td>&nbsp;</td><td>Nom</td><td>Prenom</td><td>Type permis</td><td>Nb reservations</td></tr>";
        Integer counter = 0;
        String background;

        for (Client clientUniq : listeClient) {
            counter++;
            background = counter % 2 == 0 ? "odd" : "bg-white";

            html += "<tr class='" + background  +"'>";
            if(clientUniq.getReservations().size() == 0) {
                html += "<td class='bg-white'><a class='btn-blue' href='modifier(" + clientUniq.getId() +")'><img width=25 src='images/pencil-blue-xs.png'></a></td>"; // MODIFIER
                html += "<td class='bg-white'><a class='btn-red' href='supprimer(" + clientUniq.getId() + ")'><img width=25 src='images/trash-red-xs.png'></a></td>"; // SUPPRIMER
            } else {
                html += "<td class='bg-white'><img width=25 src='images/pencil-grey-xs.png'></td>"; // MODIFIER
                html += "<td class='bg-white'><img width=25 src='images/trash-grey-xs.png'></td>"; // SUPPRIMER
            }
            html += "<td width='150px'>" + clientUniq.getNom() + "</td>";
            html += "<td width='150px'>" + clientUniq.getPrenom() + "</td>";
            html += "<td width='150px'>" + clientUniq.getPermis().getTypePermis().getNom() + "</td>";
            html += "<td width='150px'>" + clientUniq.getReservations().size() + "</td>";
            html += "</tr>";
        }
        console.print(html);
    }

    public void ajouter() {
        Form form = new Form();

        form.addInput(new TextField("Nom:", "nom"));
        form.addInput(new TextField("Prenom:", "prenom"));

        //Adresse
        form.addInput(new TextField("Tel:", "tel"));
        form.addInput(new TextField("Mail:", "mail"));
        form.addInput(new TextField("Ville:", "ville"));
        form.addInput(new TextField("Code postal:", "code"));
        form.addInput(new TextField("Rue:", "rue"));
        form.addInput(new TextField("Numero rue:", "num"));

        //Permis de conduire
        // Recuperation des types existants
        List<TypePermis> types = typePermisService.getAll();
        if(types.size() < 1) {
            traitement();
            console.alert("Aucun type de permis est enregistré, merci de créer au minimum un type de permis avant de créer un client.");
            return;
        }
        List<String> typesList = types.stream().map(m -> {return m.getNom();}).collect(Collectors.toList());
        form.addInput(new ComboBoxStr("Type permis:", "typeP", typesList, typesList.get(0)));

        form.addInput(new TextField("Numero du permis:", "numP"));
        form.addInput(new TextField("Date obtention (dd/MM/yyyy):", "dateP"));

        List<String> checks = Arrays.asList("nom", "prenom", "tel", "mail", "ville", "code", "rue", "num", "typeP", "dateP", "numP");
        EmptyValuesCheck checksAll = new EmptyValuesCheck(checks, new ClientFormValidator(), null);

        boolean validationBase = console.input("Ajout d'un client", form, checksAll);
        if(validationBase) {
            SystemOutPrintHelper.messageJaune("TOUT OK CLIENT");
            //creation du client
            Client client = new Client();
            client.setNom(form.getValue("nom"));
            client.setPrenom(form.getValue("prenom"));

            //creation de l'adresse
            Adresse adresse = new Adresse();
            adresse.setVille(form.getValue("ville"));
            adresse.setCodePostal(form.getValue("code"));
            adresse.setRue(form.getValue("rue"));
            adresse.setNumeroRue(form.getValue("num"));
            adresse.setMail(form.getValue("mail"));
            adresse.setTel(form.getValue("tel"));
            //adresse.setClient(client);

            client.setAdresse(adresse);

            //creation du permis
            Permis permis = new Permis();
            permis.setNumero(Long.parseLong(form.getValue("numP")));
            permis.setTypePermis(typePermisService.getByKey("nom", form.getValue("typeP")));
            permis.setObtention(DateHelper.convert(form.getValue("dateP")));
            //permis.setClient(client);

            client.setPermis(permis);

            clientService.create(client);
        }
        this.traitement();
    }


    public void modifier(Long id) {
        Form form = new Form();
        Client clientBefore = clientService.getById(id);

        form.addInput(new TextField("Nom:", "nom", clientBefore.getNom() ));
        form.addInput(new TextField("Prenom:", "prenom", clientBefore.getPrenom() ));

        //Adresse
        form.addInput(new TextField("Tel:", "tel", clientBefore.getAdresse().getTel() ));
        form.addInput(new TextField("Mail:", "mail", clientBefore.getAdresse().getMail() ));
        form.addInput(new TextField("Ville:", "ville", clientBefore.getAdresse().getVille() ));
        form.addInput(new TextField("Code postal:", "code", clientBefore.getAdresse().getCodePostal() ));
        form.addInput(new TextField("Rue:", "rue", clientBefore.getAdresse().getRue() ));
        form.addInput(new TextField("Numero rue:", "num", clientBefore.getAdresse().getNumeroRue() ));

        //Permis de conduire
        // Recuperation des types existants
        List<TypePermis> types = typePermisService.getAll();
        if(types.size() < 1) {
            traitement();
            console.alert("Aucun type de permis est enregistré, merci de créer au minimum un type de permis avant de créer un client.");
            return;
        }
        List<String> typesList = types.stream().map(m -> {return m.getNom();}).collect(Collectors.toList());
        form.addInput(new ComboBoxStr("Type permis:", "typeP", typesList, clientBefore.getPermis().getTypePermis().getNom() ));

        form.addInput(new TextField("Numero du permis:", "numP", "" +clientBefore.getPermis().getNumero() ));
        form.addInput(new TextField("Date obtention:", "dateP", "" + DateHelper.formatted(clientBefore.getPermis().getObtention()) ));

        List<String> checks = Arrays.asList("nom", "prenom", "tel", "mail", "ville", "code", "rue", "num", "typeP", "dateP", "numP");
        EmptyValuesCheck checksAll = new EmptyValuesCheck(checks, new ClientFormValidator(), id);

        boolean validationBase = console.input("Ajout d'un client", form, checksAll);
        if(validationBase) {

            clientBefore.setNom(form.getValue("nom"));
            clientBefore.setPrenom(form.getValue("prenom"));

            //creation de l'adresse
            clientBefore.getAdresse().setVille(form.getValue("ville"));
            clientBefore.getAdresse().setCodePostal(form.getValue("code"));
            clientBefore.getAdresse().setRue(form.getValue("rue"));
            clientBefore.getAdresse().setNumeroRue(form.getValue("num"));
            clientBefore.getAdresse().setMail(form.getValue("mail"));
            clientBefore.getAdresse().setTel(form.getValue("tel"));

            clientBefore.getPermis().setNumero(Long.parseLong(form.getValue("numP")));
            clientBefore.getPermis().setTypePermis(typePermisService.getByKey("nom", form.getValue("typeP")));
            clientBefore.getPermis().setObtention(DateHelper.convert(form.getValue("dateP")));

            clientService.update(clientBefore);
        }
        this.traitement();
    }

    public void getListeCSV() {
        List<Client> listClients = clientService.getAll();

        List<String> headers = Arrays.asList("id", "nom", "prenom", "tel", "mail", "ville", "code postale");
        List<List<String>> listConteneur = new ArrayList<>();

        for (Client client : listClients) {
            List<String> listeInfos = new ArrayList<>();
            String id = "" +client.getId();
            String prenom = client.getPrenom();
            String nom = client.getNom();
            String tel = client.getAdresse().getTel();
            String mail = client.getAdresse().getMail();
            String ville = client.getAdresse().getVille();
            String postale = client.getAdresse().getCodePostal();
            listeInfos.add(id);
            listeInfos.add(prenom);
            listeInfos.add(nom);
            listeInfos.add(tel);
            listeInfos.add(mail);
            listeInfos.add(ville);
            listeInfos.add(postale);

            listConteneur.add(listeInfos);
        }

        CsvBuilder.creationFichierCSV("listeClients.csv",  ",", "\n", headers, listConteneur);
        this.traitement();
        console.alert("Fichier généré. Il a été uploadé sur internet. Il est disponible a l'adresse suivante =>  https://3.139.215.128/diginamic/listeClients.csv");
    }
}
