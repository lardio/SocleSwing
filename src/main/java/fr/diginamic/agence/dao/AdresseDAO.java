package fr.diginamic.agence.dao;
import fr.diginamic.agence.entity.client.Adresse;

public class AdresseDAO extends SourceDAO<Adresse> {

    public AdresseDAO() {
        super(Adresse.class);
        this.tableName = Adresse.class.getSimpleName();
    }

}