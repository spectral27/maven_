package spc;

import com.fasterxml.jackson.core.JsonProcessingException;
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

        List<JavaObject> javaObjects = List.of(
                new JavaObject(1, System.getProperty("java.vendor"), System.getProperty("java.version")),
                new JavaObject(2, "Jersey Framework", "2.39.1"),
                new JavaObject(3, "Hibernate ORM", "5.6.15"),
                new JavaObject(99, "To Delete", "1.0.0")
        );

        JavaObjectClient client = new JavaObjectClient();

        // Insert
        for (JavaObject javaObject : javaObjects) {
            client.insert(javaObject);
        }

        JavaObjectRepository repository = new JavaObjectRepository();
        System.out.println(repository.getIds());

        // Update
        JavaObject forUpdate = new JavaObject();
        forUpdate.setVersion("17.0.7");
        client.update(1, forUpdate);

        // Delete
        client.delete(99);

        // Select
        javaObjects = client.select();

        JacksonPrettyPrinter jacksonPrettyPrinter = new JacksonPrettyPrinter();
        try {
            jacksonPrettyPrinter.print(javaObjects);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        System.exit(0);
    }

}
