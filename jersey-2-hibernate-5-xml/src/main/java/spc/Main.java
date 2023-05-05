package spc;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.internal.inject.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import java.net.URI;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        System.setProperty("java.util.logging.config.class", "spc.LoggerSystemOut");

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

        JavaObject java = new JavaObject(1, System.getProperty("java.vendor"), System.getProperty("java.version"));
        JavaObject jersey = new JavaObject(2, "Jersey Framework", "3.1.1");
        JavaObject hibernate = new JavaObject(3, "Hibernate ORM", "6.2.2");
        JavaObject toDelete = new JavaObject(4, "", "");

        List<JavaObject> javaObjects = List.of(java, jersey, hibernate, toDelete);

        Client client = ClientBuilder.newClient();

        // Insert
        for (JavaObject javaObject : javaObjects) {
            client.target("http://0.0.0.0:8080/objects")
                    .request()
                    .post(Entity.entity(javaObject, MediaType.APPLICATION_JSON))
                    .close();
        }

        // Update
        java.setVersion("17.0.7");

        client.target("http://0.0.0.0:8080/objects")
                .path("1")
                .request()
                .put(Entity.entity(java, MediaType.APPLICATION_JSON))
                .close();

        // Delete
        client.target("http://0.0.0.0:8080/objects")
                .path("4")
                .request()
                .delete()
                .close();

        // Select
        javaObjects = client.target("http://0.0.0.0:8080/objects")
                .request()
                .get(new GenericType<>() {});

        client.close();

        JacksonPrettyPrinter jacksonPrettyPrinter = new JacksonPrettyPrinter();
        try {
            jacksonPrettyPrinter.print(javaObjects);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        System.exit(0);
    }

}
