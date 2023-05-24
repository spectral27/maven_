package spc;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
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

    public void updateJavaObject(int id, JavaObject javaObject) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        JavaObject javaObjectToUpdate = session.find(JavaObject.class, id);
        javaObjectToUpdate.setVersion(javaObject.getVersion());
        session.merge(javaObjectToUpdate);
        session.getTransaction().commit();
        session.close();
    }

    public void update(int id, JavaObject javaObject) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Query updateQuery = session.createQuery(
                "update JavaObject j set j.version = :version where j.id = :id"
        );
        updateQuery.setParameter("version", javaObject.getVersion());
        updateQuery.setParameter("id", id);
        updateQuery.executeUpdate();
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

    public void delete(int id) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Query deleteQuery = session.createQuery(
                "delete from JavaObject j where j.id = :id"
        );
        deleteQuery.setParameter("id", id);
        deleteQuery.executeUpdate();
        session.getTransaction().commit();
        session.close();
    }

    public List<Integer> getIds() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        List<Integer> ids = session.createQuery("select j.id from JavaObject j", Integer.class)
                .getResultList();

        session.getTransaction().commit();
        session.close();
        return ids;
    }

    public List<Integer> getIdsCriteriaBuilder() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        CriteriaBuilder criteriaBuilder = sessionFactory.getCriteriaBuilder();
        CriteriaQuery<Integer> criteriaQuery = criteriaBuilder.createQuery(Integer.class);
        Root<JavaObject> root = criteriaQuery.from(JavaObject.class);
        criteriaQuery.select(root.get(JavaObject_.id));
        List<Integer> ids = session.createQuery(criteriaQuery).getResultList();

        session.getTransaction().commit();
        session.close();
        return ids;
    }

}
