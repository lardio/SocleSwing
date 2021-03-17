package fr.diginamic.agence.dao;

import fr.diginamic.agence.entity.reservation.Facture;

public class FactureDAO extends SourceDAO<Facture> {

    public FactureDAO() {
        super(Facture.class);
        this.tableName = Facture.class.getSimpleName();
    }

    public Long getLastId() {
        Integer lastId = em.createQuery("SELECT id FROM facture ORDER BY id DESC LIMIT 1").getFirstResult();
        Long id = Long.valueOf(lastId);
        return id;
    }
}
