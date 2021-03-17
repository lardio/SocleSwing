package fr.diginamic.agence.dao;

import fr.diginamic.agence.entity.vehicule.Camion;

public class CamionDAO extends SourceDAO<Camion> {

    public CamionDAO() {
        super(Camion.class);
        this.tableName = Camion.class.getSimpleName();
    }

}
