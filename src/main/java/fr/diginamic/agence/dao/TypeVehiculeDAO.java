package fr.diginamic.agence.dao;

import fr.diginamic.agence.entity.vehicule.TypeVehicule;

public class TypeVehiculeDAO extends SourceDAO<TypeVehicule> {

    public TypeVehiculeDAO() {
        super(TypeVehicule.class);
        this.tableName = TypeVehicule.class.getSimpleName();
    }

}
