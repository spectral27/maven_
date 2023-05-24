package spc;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import java.util.List;

public class JavaObjectClient {

    private final Client client = ClientBuilder.newClient();
    private final String url = "http://0.0.0.0:8080/objects";

    public void insert(JavaObject javaObject) {
        client.target(url)
                .request()
                .post(Entity.entity(javaObject, MediaType.APPLICATION_JSON))
                .close();
    }

    public void update(JavaObject javaObject) {
        client.target(url)
                .path(String.valueOf(javaObject.getId()))
                .request()
                .put(Entity.entity(javaObject, MediaType.APPLICATION_JSON))
                .close();
    }

    public void delete(JavaObject javaObject) {
        client.target(url)
                .path(String.valueOf(javaObject.getId()))
                .request()
                .delete()
                .close();
    }

    public List<JavaObject> select() {
        return client.target(url)
                .request()
                .get(new GenericType<>() {});
    }

}
