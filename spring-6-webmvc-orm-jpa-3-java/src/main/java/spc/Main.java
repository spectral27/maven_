package spc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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

        JavaObjectClient client = new JavaObjectClient();

        JavaObject java = new JavaObject();
        java.setId(1);
        java.setVendor(System.getProperty("java.vendor"));
        java.setVersion(System.getProperty("java.version"));

        client.insertObject(java);

        JavaObject spring = new JavaObject();
        spring.setId(2);
        spring.setVendor("Spring Framework");
        spring.setVersion("6.0.8");

        client.insertObject(spring);

        JavaObject jpa = new JavaObject();
        jpa.setId(3);
        jpa.setVendor("Jakarta Persistence API");
        jpa.setVersion("3.1.0");

        client.insertObject(jpa);

        JavaObject hibernate = new JavaObject();
        hibernate.setId(4);
        hibernate.setVendor("Hibernate ORM");
        hibernate.setVersion("6.2.1");

        client.insertObject(hibernate);

        List<JavaObject> javaObjects = client.selectObjects();

        ObjectMapper jackson = new ObjectMapper();

        try {
            String json = jackson.writerWithDefaultPrettyPrinter()
                    .writeValueAsString(javaObjects);
            System.out.println(json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        System.exit(1);
    }

}
