package spc;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class JavaObjectRepository {
    
    private static final EntityManagerFactory entityManagerFactory;

    static {
        entityManagerFactory = Persistence.createEntityManagerFactory("mainpersistenceunit");
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

    public void updateJavaObject(int id, JavaObject javaObject) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        JavaObject javaObjectToUpdate = entityManager.find(JavaObject.class, id);
        javaObjectToUpdate.setVersion(javaObject.getVersion());
        entityManager.merge(javaObjectToUpdate);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public void deleteJavaObject(int id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        JavaObject javaObject = entityManager.find(JavaObject.class, id);
        entityManager.remove(javaObject);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

}
