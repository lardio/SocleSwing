package fr.diginamic.swing.serviceAgence.Paiement;

import fr.diginamic.agence.entity.client.TypePermis;
import fr.diginamic.agence.entity.reservation.Facture;
import fr.diginamic.agence.entity.reservation.ModePaiement;
import fr.diginamic.agence.entity.reservation.Paiement;
import fr.diginamic.agence.entity.reservation.StatutFacture;
import fr.diginamic.agence.entity.vehicule.Vehicule;
import fr.diginamic.agence.helpers.SystemOutPrintHelper;
import fr.diginamic.agence.service.FactureService;
import fr.diginamic.agence.service.PaiementService;
import fr.diginamic.swing.composants.MenuService;
import fr.diginamic.swing.composants.ui.ComboBoxStr;
import fr.diginamic.swing.composants.ui.Form;
import fr.diginamic.swing.serviceAgence.EmptyValuesCheck;
import fr.diginamic.swing.serviceAgence.Paiement.Validator.PaiementFormValidator;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AddPaiementServiceSwing extends MenuService {

    protected PaiementService paiementService;
    protected FactureService factureService;

    public AddPaiementServiceSwing() {
        this.paiementService = new PaiementService();
        this.factureService = new FactureService();
    }

    @Override
    public void traitement() {
        List<Facture> listefactures = factureService.getListByKey("statut", StatutFacture.ATTENTE);
        SystemOutPrintHelper.messageJaune(listefactures.size() + "");
        console.clear();
        console.println("<h1 class='bg-dark-blue' padding='10px'><center>Ajouter un paiement</center></h1>");
        String html = "";

        if(listefactures.size() == 0 || listefactures == null) {
            console.println("<h1 class='bg-dark-blue' padding='10px'><center>Il y'a aucun paiement en attente. Toutes les factures sont réglées.</center></h1>");
        } else {
            html += "<table class='table' cellspacing=0> <tr class='bg-dark-blue'>";
            html += "<td>&nbsp;</td><td>Client</td><td>Reservation</td><td>Num facture</td><td>Montant</td><td>Statut</td></tr>";

            for (Facture facture : listefactures) {
                html += "<tr><td><a class='btn-blue' href='ajouter(" + facture.getId() + ")'><img width=25 src='images/plus-blue.png'></a></td>";
                html += "<td width='120px'>" + facture.getClient().getNom() + " " + facture.getClient().getPrenom() + "</td>";
                html += "<td width='120px'>" + facture.getReservation().getId() + "</td>";
                html += "<td width='120px'>" + facture.getNumero() + "</td>";
                html += "<td width='120px'>" + facture.getCout() + "</td>";
                html += "<td width='120px'>" + facture.getStatut() + "</td>";
            }
            html += "</table>";
        }
        console.println(html);
    }

    public void ajouter(Long id) {
        Form form = new Form();

        //Mode de paiement
        // Recuperation des types existants
        List<String> enumValues = Stream.of(ModePaiement.values()).map(Enum::name).collect(Collectors.toList());
        form.addInput(new ComboBoxStr("Mode paiement:", "mode", enumValues, enumValues.get(0) ));

        List<String> listeChecks = Arrays.asList("mode");
        EmptyValuesCheck checks = new EmptyValuesCheck(listeChecks, new PaiementFormValidator(), null);
        boolean validation = console.input("Ajout d'un paiement", form, checks);
        if (validation) {
            Paiement paiement = new Paiement();
            paiement.setFacture(factureService.getById(id));
            paiement.setModePaiement(ModePaiement.valueOf(form.getValue("mode")));
            paiement.getFacture().setStatut(StatutFacture.PAYE);
            paiement.getFacture().setPaiement(paiement);
            paiementService.create(paiement);
            factureService.update(paiement.getFacture());
        }
        this.traitement();
    }
}
