package fr.diginamic.agence.service;

import fr.diginamic.agence.dao.PaiementDAO;
import fr.diginamic.agence.entity.reservation.Paiement;
import fr.diginamic.agence.entity.reservation.StatutFacture;
import fr.diginamic.agence.helpers.SystemOutPrintHelper;

public class PaiementService extends SourceService<PaiementDAO, Paiement> {

    public PaiementService() {
        this.entityDAO = new PaiementDAO();
    }

}