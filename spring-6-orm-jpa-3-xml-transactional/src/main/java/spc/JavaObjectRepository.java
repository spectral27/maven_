package spc;

import jakarta.persistence.EntityManager;

import java.util.List;

public class JavaObjectRepository {

    private final EntityManager entityManager;

    public JavaObjectRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void insertJavaObject(JavaObject javaObject) {
        entityManager.persist(javaObject);
    }

    public List<JavaObject> selectJavaObjects() {
        return entityManager.createQuery("select j from JavaObject j", JavaObject.class).getResultList();
    }

}
