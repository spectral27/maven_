package spc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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

    private final ObjectMapper jackson = new ObjectMapper();

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
        JavaObjectClient client = new JavaObjectClient();

        JavaObject junit = new JavaObject();
        junit.setId(1);
        junit.setVendor("JUnit Jupiter");
        junit.setVersion("5.9.2");

        client.insertObject(junit);

        List<JavaObject> javaObjects = client.selectObjects();

        Assertions.assertNotNull(javaObjects);
        Assertions.assertEquals(1, javaObjects.size());

        try {
            String json = jackson.writerWithDefaultPrettyPrinter()
                    .writeValueAsString(javaObjects);
            System.out.println(json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
