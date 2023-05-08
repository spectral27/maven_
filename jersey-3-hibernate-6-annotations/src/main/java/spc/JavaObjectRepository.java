package spc;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.List;

public class JavaObjectRepository {
    
    private static final SessionFactory sessionFactory;

    static {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
        sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
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
                .getResultList();
        session.getTransaction().commit();
        session.close();
        return javaObjects;
    }

    public void updateJavaObject(int id, String version) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        JavaObject javaObject = session.find(JavaObject.class, id);
        javaObject.setVersion(version);
        session.merge(javaObject);
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
