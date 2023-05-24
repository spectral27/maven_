package spc;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import java.util.List;

public class JavaObjectClient {

    private final Client client = ClientBuilder.newClient();
    private final String url = "http://localhost:8080/objects";

    public void insert(JavaObject javaObject) {
        client.target(url)
                .request()
                .post(Entity.entity(javaObject, MediaType.APPLICATION_JSON))
                .close();
    }

    public void update(int id, JavaObject javaObject) {
        client.target(url)
                .path(String.valueOf(id))
                .request()
                .put(Entity.entity(javaObject, MediaType.APPLICATION_JSON))
                .close();
    }

    public void delete(int id) {
        client.target(url)
                .path(String.valueOf(id))
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
