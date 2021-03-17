package fr.diginamic.swing.serviceAgence.Paiement;

import fr.diginamic.agence.entity.reservation.Facture;
import fr.diginamic.agence.entity.reservation.StatutFacture;
import fr.diginamic.agence.helpers.SystemOutPrintHelper;
import fr.diginamic.agence.service.FactureService;
import fr.diginamic.agence.service.PaiementService;
import fr.diginamic.swing.composants.MenuService;

import java.util.List;

public class ListAllPaiementEnded extends MenuService {

    protected PaiementService paiementService;
    protected FactureService factureService;

    public ListAllPaiementEnded() {
        this.paiementService = new PaiementService();
        this.factureService = new FactureService();
    }

    @Override
    public void traitement() {
        List<Facture> listefactures = factureService.getListByKey("statut", StatutFacture.PAYE);
        SystemOutPrintHelper.messageJaune(listefactures.size() + "");
        console.clear();
        console.println("<h1 class='bg-dark-blue' padding='10px'><center>Liste des paiements terminés</center></h1>");
        String html = "";

        html += "<table class='table' cellspacing=0> <tr class='bg-dark-blue'>";
        html += "<td>Client</td><td>Num reservation</td><td>Num facture</td><td>Montant</td><td>Mode paiement</td><td>Statut</td></tr>";

        for (Facture facture : listefactures) {
            html += "<tr><td width='120px'>" + facture.getClient().getNom() + " " + facture.getClient().getPrenom() + "</td>";
            html += "<td width='150px'>" + facture.getReservation().getId() + "</td>";
            html += "<td width='120px'>" + facture.getNumero() + "</td>";
            html += "<td width='120px'>" + facture.getCout() + "</td>";
            html += "<td width='120px'>" + facture.getPaiement().getModePaiement() + "</td>";
            html += "<td width='120px'>" + facture.getStatut() + "</td></tr>";
        }
        html += "</table>";
        console.println(html);
    }
}