package spc;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

public class JavaObjectRepository {
    
    private final EntityManagerFactory entityManagerFactory;

    public JavaObjectRepository(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    public void insertJavaObject(JavaObject javaObject) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(javaObject);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public List<JavaObject> selectJavaObjects() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        List<JavaObject> javaObjects = entityManager
                .createQuery("select j from JavaObject j", JavaObject.class)
                .getResultList();
        entityManager.getTransaction().commit();
        entityManager.close();
        return javaObjects;
    }

}
