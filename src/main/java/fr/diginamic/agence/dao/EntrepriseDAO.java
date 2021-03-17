package fr.diginamic.agence.dao;

import fr.diginamic.agence.entity.agence.Entreprise;

public class EntrepriseDAO extends SourceDAO<Entreprise> {

    public EntrepriseDAO() {
        super(Entreprise.class);
        this.tableName = Entreprise.class.getSimpleName();
    }
}