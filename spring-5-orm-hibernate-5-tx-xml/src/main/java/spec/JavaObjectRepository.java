package spec;

import org.hibernate.SessionFactory;

import java.util.List;

public class JavaObjectRepository {

    private final SessionFactory sessionFactory;

    public JavaObjectRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void insert(JavaObject javaObject) {
        sessionFactory.getCurrentSession().save(javaObject);
    }

    public List<JavaObject> selectAll() {
        return sessionFactory.getCurrentSession()
                .createQuery("select j from JavaObject j", JavaObject.class)
                .getResultList();
    }

    public JavaObject select(int id) {
        return sessionFactory.getCurrentSession().get(JavaObject.class, id);
    }

    public void update(JavaObject javaObject) {
        sessionFactory.getCurrentSession().update(javaObject);
    }

    public void delete(int id) {
        sessionFactory.getCurrentSession()
                .createQuery("delete from JavaObject j where j.id = :id")
                .setParameter("id", id)
                .executeUpdate();
    }

}
