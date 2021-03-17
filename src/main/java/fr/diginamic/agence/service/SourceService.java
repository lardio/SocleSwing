package fr.diginamic.agence.service;
import fr.diginamic.agence.EntityManagerGestion;
import fr.diginamic.agence.dao.SourceDAO;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;

public abstract class SourceService<T extends SourceDAO, E> {

    protected T entityDAO;
    protected EntityManager em;
    protected E entiteClass;

    public SourceService() {
        this.em = EntityManagerGestion.recuperationEntityManager();
    }

    public List<E> getAll() {
        List<E> entites = null;
        try {
            entites =  entityDAO.findAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return entites;
    }

    public <K> List<E> getAllWithJustValue(K k) {
        List<E> entites = null;
        try {
            entites =  entityDAO.findAllWithOnlyOneValue(k);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return entites;
    }

    public E getById(Long id) {
        E entite = null;
        try {
            entite = (E) entityDAO.findByID(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return entite;
    }

    public <K, V> E getByKey(K k, V v) {
        E entite = null;
        try {
            entite = (E) entityDAO.findByKey(k, v);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return entite;
    }

    public <K, V> List<E> getListByKey(K k, V v) {
        List<E> entites = null;
        try {
            entites =  entityDAO.findListByKey(k, v);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return entites;
    }

    public E update(E entite) {
        EntityTransaction tx = null;
        try {
            tx = em.getTransaction();
            tx.begin();
            entityDAO.update(entite);
            tx.commit();
        } catch (Exception e) {
            if (tx!=null) {
                tx.rollback();
            }
            e.printStackTrace();
        }
        return entite;
    }

    public E create(E entite) {
        EntityTransaction tx = null;
        try {
            tx = em.getTransaction();
            tx.begin();
            entityDAO.add(entite);
            tx.commit();
        } catch (Exception e) {
            if (tx!=null) {
                tx.rollback();
            }
            e.printStackTrace();
        }
        return entite;
    }

    public void delete(E entite) {
        EntityTransaction tx = null;
        try {
            tx = em.getTransaction();
            tx.begin();
            entityDAO.delete(entite);
            tx.commit();
        } catch (Exception e) {
            if (tx!=null) {
                tx.rollback();
            }
            e.printStackTrace();
        }
    }



}
