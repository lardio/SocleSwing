package fr.diginamic.agence;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntityManagerGestion {

    private static final ThreadLocal<EntityManager> entityManagerThreadLocal = new ThreadLocal<>();
    private static EntityManagerFactory entityManagerFactory = creationEntityManagerFactory();
    private static EntityManager entityManager = null;

    private static EntityManagerFactory creationEntityManagerFactory(){
        return Persistence.createEntityManagerFactory("tp_vehicule");
    }

    public EntityManagerGestion(){
    }

    public static EntityManager recuperationEntityManager() {
        if (entityManager == null) {
            // Créé un nouveau si pas déjà fait, sinon renvoi le courant
            System.out.println("Creation nouvel em");
            entityManager = entityManagerFactory.createEntityManager();
        }
        return entityManager;
    }

}