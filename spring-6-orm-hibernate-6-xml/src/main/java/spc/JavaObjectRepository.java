package spc;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

public class JavaObjectRepository {
    
    private final SessionFactory sessionFactory;

    public JavaObjectRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void insertJavaObject(JavaObject javaObject) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.persist(javaObject);
        session.getTransaction().commit();
        session.close();
    }

    public List<JavaObject> selectJavaObjects() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List<JavaObject> javaObjects = session
                .createQuery("select j from JavaObject j", JavaObject.class)
                .list();
        session.getTransaction().commit();
        session.close();
        return javaObjects;
    }

    public void updateJavaObject(int id, JavaObject javaObject) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        JavaObject javaObjectToUpdate = session.find(JavaObject.class, id);
        javaObjectToUpdate.setVersion(javaObject.getVersion());
        session.merge(javaObjectToUpdate);
        session.getTransaction().commit();
        session.close();
    }

    public void deleteJavaObject(int id) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        JavaObject javaObject = session.find(JavaObject.class, id);
        session.remove(javaObject);
        session.getTransaction().commit();
        session.close();
    }

}
