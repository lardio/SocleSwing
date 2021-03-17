package fr.diginamic.agence.dao;

import fr.diginamic.agence.entity.vehicule.Commentaire;

public class CommentaireDAO extends SourceDAO<Commentaire> {

    public CommentaireDAO() {
        super(Commentaire.class);
        this.tableName = Commentaire.class.getSimpleName();
    }

}
