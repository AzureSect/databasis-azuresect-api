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
        Material created = materialService.create(material);
        return Response.status(Response.Status.CREATED).entity(created).build();
    }

    @PUT
    @Path("/{id}")
    public Material update(@PathParam("id") Long id, Material material) {
        return materialService.update(id, material);
}

}
