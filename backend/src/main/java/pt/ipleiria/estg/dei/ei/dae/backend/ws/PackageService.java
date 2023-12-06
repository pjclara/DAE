package pt.ipleiria.estg.dei.ei.dae.backend.ws;

import jakarta.ejb.EJB;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pt.ipleiria.estg.dei.ei.dae.backend.dtos.PackageDTO;
import pt.ipleiria.estg.dei.ei.dae.backend.ejbs.PackageBean;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.Package;
import pt.ipleiria.estg.dei.ei.dae.backend.exceptions.MyEntityNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Path("/packages")
@Produces({MediaType.APPLICATION_JSON}) // injects header “Content-Type: application/json”
@Consumes({MediaType.APPLICATION_JSON}) // injects header “Accept: application/json”
public class PackageService {

    @EJB
    private PackageBean packageBean;

    @GET
    @Path("/")
    public Response getDocuments() {
        var packages = packageBean.all();
        return Response.ok(PackageDTO.from(packages)).build();
    }

    @GET
    @Path("{code}")
    public Response get(@PathParam("code") long code) {
        return Response.ok(PackageDTO.from(packageBean.find(code))).build();
    }

    @POST
    @Path("")
    //@Authenticated
    //@RolesAllowed({"...Something..."})
    public Response create(PackageDTO package_) {
        packageBean.create(
                package_.getPackagingCode(),
                package_.getPackagingType(),
                package_.getPackagingMaterial()
        );

        var dto = PackageDTO.from(packageBean.find(package_.getPackagingCode()));
        return Response.status(Response.Status.CREATED).entity(dto).build();
    }

    @DELETE
    @Path("{code}")
    public Response delete(@PathParam("code") long code) throws MyEntityNotFoundException {
        packageBean.remove(code);
        return Response.noContent().build();
    }

}
