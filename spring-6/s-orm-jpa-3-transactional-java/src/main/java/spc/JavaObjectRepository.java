package spc;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;

public class JavaObjectRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public void insertJavaObject(JavaObject javaObject) {
        entityManager.persist(javaObject);
    }

    public List<JavaObject> selectJavaObjects() {
        return entityManager.createQuery("select j from JavaObject j", JavaObject.class).getResultList();
    }

}
