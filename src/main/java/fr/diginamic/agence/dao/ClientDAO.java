package fr.diginamic.agence.dao;
import fr.diginamic.agence.entity.client.Client;

public class ClientDAO extends SourceDAO<Client> {

    public ClientDAO() {
        super(Client.class);
        this.tableName = Client.class.getSimpleName();
    }

}