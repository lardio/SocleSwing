package fr.diginamic.agence.dao;

import fr.diginamic.agence.EntityManagerGestion;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

/**
 * Cette classe définit les méthodes habituelles de type CRUD.
 * Objectif est d'alleger le reste des DAO au minimum.
 *
 * @param <T>
 * @author Sylvain
 */
public abstract class SourceDAO<T> {

    protected EntityManager em;
    protected Class<T> classe;
    protected String tableName;

    public SourceDAO (Class<T> classe) {
        this.em = EntityManagerGestion.recuperationEntityManager();
        this.classe = classe;
    }

    public List<T> findAll () {
        List<T> listeRetour = new ArrayList<>();
        String request = "SELECT t from " +this.tableName + " t";
        TypedQuery<T> finalRequest = em.createQuery(request, classe);
        listeRetour = finalRequest.getResultList();
        return listeRetour;
    }

    public <K> List<T> findAllWithOnlyOneValue (K key) {
        List<T> listeRetour = new ArrayList<>();
        String request = "SELECT t.?0 from " +this.tableName + " t";
        TypedQuery<T> finalRequest = em.createQuery(request, classe);
        finalRequest.setParameter(0, key);
        listeRetour = finalRequest.getResultList();
        return listeRetour;
    }

    /**
     * Peremt de faire une recherche en filtrant par un champ donné. Utilisé pour voir si une entrée existe
     * @param key  de type generique
     * @param <K>
     * @param value de type generique
     * @param <V>
     * @return T - classe
     */
    public <K, V> T findByKey (K key, V value) {
        String request = "SELECT t from " +this.tableName + " t WHERE t." +key + " = ?0";
        TypedQuery<T> finalRequest = em.createQuery(request, classe);
        finalRequest.setParameter(0, value);
        finalRequest.setMaxResults(1);
        List<T> resultFromDB = finalRequest.getResultList();
        if (!resultFromDB.isEmpty()) {
            return (T) resultFromDB.get(0); //cast du retour vers T
        }
        return null;
    }

    /**
     * Peremt de faire une recherche en filtrant par un champ donné. Utilisé pour voir si une entrée existe
     * @param key  de type generique
     * @param <K>
     * @param value de type generique
     * @param <V>
     * @return T - classe
     */
    public <K, V> List<T> findListByKey (K key, V value) {
        List<T> listeRetour = new ArrayList<>();
        String request = "SELECT t from " +this.tableName + " t WHERE t." +key + " = ?0";
        TypedQuery<T> finalRequest = em.createQuery(request, classe);
        finalRequest.setParameter(0, value);
        listeRetour = finalRequest.getResultList();
        return listeRetour;
    }

    /**
     * Recherche via un ID.
     * @param id
     * @return T - classe
     */
    public T findByID (Long id) {
        return (T) em.find(classe, id);
    }

    /**
     * Ajoute une entité en BDD
     * @param entite
     */
    public void add(T entite) {
        em.persist(entite);
    }

    /**
     * Met a jour une entite en BDD
     * @param entite
     */
    public void update(T entite) {
        em.merge(entite);
    }

    /**
     * Supprime une entite en BDD
     * @param entite
     */
    public void delete(T entite) {em.remove(entite);}
}
