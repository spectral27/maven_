package spc;

import java.util.List;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/objects")
public class JavaObjectController {

    @Inject
    private JavaObjectRepository repository;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response selectJavaObjects() {
        List<JavaObject> javaObjects = repository.selectJavaObjects();
        return Response.status(Response.Status.OK).entity(javaObjects).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response insertJavaObject(JavaObject javaObject) {
        repository.insertJavaObject(javaObject);
        return Response.status(Response.Status.OK).build();
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateJavaObject(@PathParam("id") int id, JavaObject javaObject) {
        repository.updateJavaObject(id, javaObject.getVersion());
        return Response.status(Response.Status.OK).build();
    }

}
