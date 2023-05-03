package spc;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class MainTest {

    @BeforeAll
    public static void beforeAll() {
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
    }

    @Test
    public void mainTest() {
        JavaObject junit = new JavaObject(1, "JUnit Jupiter", "5.9.2");

        JavaObjectClient client = new JavaObjectClient();
        client.insertObject(junit);

        List<JavaObject> javaObjects = client.selectObjects();

        Assertions.assertNotNull(javaObjects);
        Assertions.assertEquals(1, javaObjects.size());

        try {
            JacksonPrettyPrinter jacksonPrettyPrinter = new JacksonPrettyPrinter();
            jacksonPrettyPrinter.print(javaObjects);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
