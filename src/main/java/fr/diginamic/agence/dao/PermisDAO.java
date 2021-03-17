package fr.diginamic.agence.dao;
import fr.diginamic.agence.entity.client.Permis;

public class PermisDAO extends SourceDAO<Permis> {

    public PermisDAO() {
        super(Permis.class);
        this.tableName = Permis.class.getSimpleName();
    }

}