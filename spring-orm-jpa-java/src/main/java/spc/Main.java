package spc;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        try {
            Class.forName("org.h2.Driver");
            DriverManager.getConnection(
                    "jdbc:h2:mem:database01;INIT=runscript from 'classpath:data.sql'",
                    "username",
                    "password"
            );
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            System.exit(1);
        }

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpringBeans.class);

        JavaObjectRepository repository = context.getBean("javaObjectRepository", JavaObjectRepository.class);

        JavaObject java = new JavaObject();
        java.setId(1);
        java.setVendor(System.getProperty("java.vendor"));
        java.setVersion(System.getProperty("java.version"));

        repository.insertJavaObject(java);

        JavaObject spring = new JavaObject();
        spring.setId(2);
        spring.setVendor("Spring Framework");
        spring.setVersion("6.0.8");

        repository.insertJavaObject(spring);

        JavaObject jpa = new JavaObject();
        jpa.setId(3);
        jpa.setVendor("Jakarta Persistence API");
        jpa.setVersion("3.1.0");

        repository.insertJavaObject(jpa);

        JavaObject hibernate = new JavaObject();
        hibernate.setId(4);
        hibernate.setVendor("Hibernate ORM");
        hibernate.setVersion("6.2.1");

        repository.insertJavaObject(hibernate);

        List<JavaObject> javaObjects = repository.selectJavaObjects();

        for (JavaObject javaObject : javaObjects) {
            System.out.printf("%s %s\n", javaObject.getVendor(), javaObject.getVersion());
        }

        context.close();
    }

}
