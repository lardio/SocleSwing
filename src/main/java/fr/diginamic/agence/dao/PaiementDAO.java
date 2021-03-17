package fr.diginamic.agence.dao;
import fr.diginamic.agence.entity.reservation.Paiement;

public class PaiementDAO extends SourceDAO<Paiement> {

    public PaiementDAO() {
        super(Paiement.class);
        this.tableName = Paiement.class.getSimpleName();
    }

}