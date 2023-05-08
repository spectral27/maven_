package spc;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        try {
            Class.forName("org.h2.Driver");
            DriverManager.getConnection(
                    "jdbc:h2:mem:database01;INIT=runscript from 'classpath:init.sql'",
                    "username",
                    "password"
            );
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            System.exit(1);
        }

        JavaObject java = new JavaObject(1, System.getProperty("java.vendor"), System.getProperty("java.version"));
        JavaObject spring = new JavaObject(2, "Spring Framework", "6.0.8");
        JavaObject jpa = new JavaObject(3, "Jakarta Persistence API", "3.1.0");
        JavaObject hibernate = new JavaObject(4, "Hibernate ORM", "6.2.2");
        JavaObject toDelete = new JavaObject(99, "To Delete", "1.0.0");

        List<JavaObject> javaObjects = List.of(java, spring, jpa, hibernate, toDelete);

        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("beans1.xml");

        JavaObjectRepository repository = context.getBean("javaObjectRepository", JavaObjectRepository.class);

        for (JavaObject javaObject : javaObjects) {
            repository.insertJavaObject(javaObject);
        }

        JavaObject updateJava = new JavaObject(1, System.getProperty("java.vendor"), "17.0.7");
        updateJava.setVersion("17.0.7");

        repository.updateJavaObject(1, updateJava);

        repository.deleteJavaObject(99);

        javaObjects = repository.selectJavaObjects();

        for (JavaObject javaObject : javaObjects) {
            System.out.printf("%s %s\n", javaObject.getVendor(), javaObject.getVersion());
        }

        context.close();
    }

}
