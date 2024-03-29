package pt.ipleiria.estg.dei.ei.dae.backend.ws;

import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pt.ipleiria.estg.dei.ei.dae.backend.dtos.PackageOrderDTO;
import pt.ipleiria.estg.dei.ei.dae.backend.dtos.PackageProductDTO;
import pt.ipleiria.estg.dei.ei.dae.backend.dtos.ProductDTO;
import pt.ipleiria.estg.dei.ei.dae.backend.ejbs.PackageOrderBean;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.PackageOrder;

import java.util.List;
import java.util.stream.Collectors;

@Path("/packageOrders")
@Produces({MediaType.APPLICATION_JSON}) // injects header “Content-Type: application/json”
@Consumes({MediaType.APPLICATION_JSON}) // injects header “Accept: application/json”

public class PackageOrderService {

    @EJB
    private PackageOrderBean packageOrderBean;

    @GET
    @Path("/")
    public List<PackageOrderDTO> getAllPackages() {
        return toDTOs(packageOrderBean.all());
    }

    private List<PackageOrderDTO> toDTOs(List<PackageOrder> all) {
        return all.stream().map(this::toDTO).collect(Collectors.toList());
    }

    private PackageOrderDTO toDTO(PackageOrder packageOrder) {
        if (packageOrder == null)
            return null;
        return new PackageOrderDTO(
                packageOrder.getId(),
                packageOrder.getPackagingType(),
                packageOrder.getPackagingMaterial()
        );

    }

    @GET
    @Path("/{id}")
    public Response getPackageById(@PathParam("id") long id) {
        PackageOrder packageOrder = packageOrderBean.find(id);
        if (packageOrder != null) {
            return Response.ok(toDTO(packageOrder)).build();
        }
        return Response.status(Response.Status.NOT_FOUND)
                .entity("ERROR_FINDING_PACKAGE")
                .build();
    }

    @POST
    @Path("/")
    public Response createNewPackage(PackageOrderDTO packageOrderDTO) {
        long id = packageOrderBean.create(packageOrderDTO);
        PackageOrder packageOrder = packageOrderBean.find(id);

        if (packageOrder == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        return Response.status(Response.Status.CREATED).entity(toDTO(packageOrder)).build();
    }

    @PUT
    @Path("/{id}")
    public Response updatePackage(@PathParam("id") long id, PackageOrderDTO packageOrderDTO) {
        PackageOrder packageOrder = packageOrderBean.update(id, packageOrderDTO);
        if (packageOrder != null) {
            return Response.ok(toDTO(packageOrder)).build();
        }
        return Response.status(Response.Status.NOT_FOUND)
                .entity("ERROR_UPDATING_PACKAGE")
                .build();
    }



}
