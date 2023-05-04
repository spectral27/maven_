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

        List<JavaObject> javaObjects = List.of(java, jersey, hibernate);

        Client client = ClientBuilder.newClient();

        for (JavaObject javaObject : javaObjects) {
            client.target("http://0.0.0.0:8080/objects")
                    .request()
                    .post(Entity.entity(javaObject, MediaType.APPLICATION_JSON))
                    .close();
        }

        java.setVersion("17.0.7");

        client.target("http://0.0.0.0:8080/objects")
                .path("1")
                .request()
                .put(Entity.entity(java, MediaType.APPLICATION_JSON))
                .close();

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
