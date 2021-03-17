package fr.diginamic.agence.dao;

import fr.diginamic.agence.entity.vehicule.Marque;

public class MarqueDAO extends SourceDAO<Marque> {

    public MarqueDAO() {
        super(Marque.class);
        this.tableName = Marque.class.getSimpleName();
    }
}
