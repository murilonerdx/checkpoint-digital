package com.fiap.DesafioDigital.rest;

import com.fiap.DesafioDigital.dao.SetupDAO;
import com.fiap.DesafioDigital.model.Setup;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/setups")
@Produces(MediaType.APPLICATION_JSON)
public class SetupsEndpoints {
    private SetupDAO dao = new SetupDAO();

    @GET
    public List<Setup> getAll() {
        return dao.getAll();
    }

    @GET
    @Path("/{id}")
    public Setup findById(@PathParam("id") Long id) {
        return dao.findById(id);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(Setup setup) {
        if (setup == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        dao.save(setup);
        return Response.status(Response.Status.CREATED).entity(setup).build();
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, Setup setup) {
        if (setup == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        setup.setId(id);
        dao.update(setup);
        return Response.status(Response.Status.ACCEPTED).build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        if (id == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        dao.delete(id);
        return Response.status(Response.Status.ACCEPTED).build();
    }

    @GET
    @Path("/search")
    public Response findByEmail(@QueryParam("email") String email) {
        return Response.status(Response.Status.ACCEPTED).entity(dao.findByEmail(email)).build();
    }


}
