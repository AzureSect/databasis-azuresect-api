package com.azuresect.resource;

import com.azuresect.model.Material;
import com.azuresect.service.MaterialService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import java.util.List;
import jakarta.ws.rs.core.Response;

@Path("/materials")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MaterialResource {

    @Inject
    MaterialService materialService;

    @GET
    public List<Material> listAll() {
        return materialService.listAll();
    }

    @POST
    public Response create(Material material) { 
        Material existing = materialService.findByName(material.getName());
        
        if (existing != null) {
            return Response.status(Response.Status.CONFLICT)
                .entity("{\"error\": \"Já existe uma matéria-prima com este nome.\"}")
                .build();
        }
        
        Material created = materialService.create(material);
        return Response.status(Response.Status.CREATED).entity(created).build();
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, Material material) {
        Material updated = materialService.update(id, material);
        return Response.ok(updated).build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        materialService.delete(id);
        return Response.noContent().build();
    }
}