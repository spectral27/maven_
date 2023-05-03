package spc;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;

import java.io.File;
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

        Tomcat tomcat = new Tomcat();
        tomcat.getConnector();
        tomcat.addWebapp("", new File(".").getAbsolutePath());

        try {
            tomcat.start();
        } catch (LifecycleException e) {
            throw new RuntimeException(e);
        }

        JavaObject java = new JavaObject(1, System.getProperty("java.vendor"), System.getProperty("java.version"));
        JavaObject spring = new JavaObject(2, "Spring Framework", "6.0.8");
        JavaObject jpa = new JavaObject(3, "Jakarta Persistence API", "3.1.0");
        JavaObject hibernate = new JavaObject(4, "Hibernate ORM", "6.2.1");

        List<JavaObject> javaObjects = List.of(java, spring, jpa, hibernate);

        JavaObjectClient client = new JavaObjectClient();

        for (JavaObject javaObject : javaObjects) {
            client.insertObject(javaObject);
        }

        javaObjects = client.selectObjects();

        JacksonPrettyPrinter jacksonPrettyPrinter = new JacksonPrettyPrinter();

        try {
            jacksonPrettyPrinter.print(javaObjects);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        System.exit(1);
    }

}
