package spc;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

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

    @DELETE
    @Path("/{id}")
    public Response deleteJavaObject(@PathParam("id") int id) {
        repository.deleteJavaObject(id);
        return Response.status(Response.Status.OK).build();
    }

}
