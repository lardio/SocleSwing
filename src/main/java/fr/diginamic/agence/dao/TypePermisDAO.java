package fr.diginamic.agence.dao;

import fr.diginamic.agence.entity.client.TypePermis;

public class TypePermisDAO extends SourceDAO<TypePermis> {

    public TypePermisDAO() {
        super(TypePermis.class);
        this.tableName = TypePermis.class.getSimpleName();
    }
}