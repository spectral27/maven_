package spec;

import org.hibernate.SessionFactory;

import java.util.List;

public class JavaObjectRepository {

    private final SessionFactory sessionFactory;

    public JavaObjectRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void insertJavaObject(JavaObject javaObject) {
        sessionFactory.getCurrentSession().save(javaObject);
    }

    public List<JavaObject> selectJavaObjects() {
        return sessionFactory.getCurrentSession()
                .createQuery("select j from JavaObject j", JavaObject.class)
                .getResultList();
    }

}
