package spc;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.internal.inject.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.net.URI;
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

        ResourceConfig resourceConfig = new ResourceConfig();
        resourceConfig.packages(Main.class.getPackageName());
        resourceConfig.register(new AbstractBinder() {
            @Override
            protected void configure() {
                bindAsContract(JavaObjectRepository.class);
            }
        });
        GrizzlyHttpServerFactory.createHttpServer(URI.create("http://0.0.0.0:8080"), resourceConfig);
    }

    @Test
    public void mainTest() {
        JavaObject junit = new JavaObject(1, "JUnit Jupiter", "5.9.2");

        Client client = ClientBuilder.newClient();

        client.target("http://0.0.0.0:8080/objects")
                .request()
                .post(Entity.entity(junit, MediaType.APPLICATION_JSON))
                .close();

        List<JavaObject> javaObjects = client.target("http://0.0.0.0:8080/objects")
                .request()
                .get(new GenericType<>() {});

        Assertions.assertNotNull(javaObjects);
        Assertions.assertEquals(1, javaObjects.size());

        JacksonPrettyPrinter jacksonPrettyPrinter = new JacksonPrettyPrinter();
        try {
            jacksonPrettyPrinter.print(javaObjects);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
