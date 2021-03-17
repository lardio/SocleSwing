package fr.diginamic.agence.service;

import fr.diginamic.agence.dao.ClientDAO;
import fr.diginamic.agence.entity.client.Client;
import fr.diginamic.agence.helpers.SystemOutPrintHelper;

public class ClientService extends SourceService<ClientDAO, Client> {

    public ClientService() {
        this.entityDAO = new ClientDAO();
    }

    public boolean checkIfAlreadyHasReservation (Client client) {
        if(client.getReservations().size() > 0) {
            SystemOutPrintHelper.messageBleu("Le client à déjà eu une reservation. Modification impossible.");
            return false;
        }
        SystemOutPrintHelper.messageJaune("Aucune reservation pour le client. Ok pour modification.");
        return true;
    }

    public void deleteClient(Client client) {
        if(this.checkIfAlreadyHasReservation(client)) {
            this.delete(client);
            SystemOutPrintHelper.messageJaune("Suppression du client OK.");
        } else {
            SystemOutPrintHelper.messageBleu("Supression du client impossible.");
        }
    }

}
