package spc;

import org.hibernate.SessionFactory;
import org.hibernate.query.MutationQuery;

import java.util.List;

public class JavaObjectRepository {
    
    private final SessionFactory sessionFactory;

    public JavaObjectRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void insertJavaObject(JavaObject javaObject) {
        sessionFactory.getCurrentSession().persist(javaObject);
    }

    public List<JavaObject> selectJavaObjects() {
        return sessionFactory.getCurrentSession()
                .createQuery("select j from JavaObject j", JavaObject.class)
                .getResultList();
    }

    public void updateJavaObject(int id, JavaObject javaObject) {
        MutationQuery mutationQuery = sessionFactory.getCurrentSession()
                .createMutationQuery("update JavaObject j set j.version = :version where j.id = :id");
        mutationQuery.setParameter("version", javaObject.getVersion());
        mutationQuery.setParameter("id", id);
        mutationQuery.executeUpdate();
    }

}
