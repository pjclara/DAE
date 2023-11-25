package pt.ipleiria.estg.dei.ei.dae.backend.ws;

import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pt.ipleiria.estg.dei.ei.dae.backend.dtos.ManufacturerDTO;
import pt.ipleiria.estg.dei.ei.dae.backend.ejbs.ManufacturerBean;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.Manufacturer;

import java.util.List;

@Path("/manufacturers")
@Produces({MediaType.APPLICATION_JSON}) // injects header “Content-Type: application/json”
@Consumes({MediaType.APPLICATION_JSON}) // injects header “Accept: application/json”
public class ManufacturerService {

    @EJB
    private ManufacturerBean manufacturerBean;

    @GET
    @Path("/")
    public List<ManufacturerDTO> getAllManufacturers() {
        return toDTOs(manufacturerBean.all());   }

    private List<ManufacturerDTO> toDTOs(List<Manufacturer> all) {
        return all.stream().map(this::toDTO).collect(java.util.stream.Collectors.toList());
    }

    private ManufacturerDTO toDTO(Manufacturer manufacturer) {
        return new ManufacturerDTO(
                manufacturer.getUsername(),
                manufacturer.getPassword(),
                manufacturer.getName(),
                manufacturer.getEmail(),
                manufacturer.getRole()
        );
    }

    @POST
    @Path("/")
    public Response createNewManufacturer(ManufacturerDTO manufacturerDTO) throws Exception {
        manufacturerBean.create(
                manufacturerDTO.getUsername(),
                manufacturerDTO.getPassword(),
                manufacturerDTO.getName(),
                manufacturerDTO.getEmail(),
                manufacturerDTO.getRole()
        );
        Manufacturer manufacturer = manufacturerBean.find(manufacturerDTO.getUsername());
        if (manufacturer == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        return Response.status(Response.Status.CREATED).entity(toDo(manufacturer)).build();
    }

    private ManufacturerDTO toDo(Manufacturer manufacturer) {
        return new ManufacturerDTO(
                manufacturer.getUsername(),
                manufacturer.getPassword(),
                manufacturer.getName(),
                manufacturer.getEmail(),
                manufacturer.getRole()
        );

    }

    @PUT
    @Path("{username}")
    public Response updateManufacturer(@PathParam("username") String username, ManufacturerDTO manufacturerDTO) {
        var manufacturer = manufacturerBean.find(username);
        if (manufacturer == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        manufacturerBean.update(
                manufacturerDTO.getUsername(),
                manufacturerDTO.getPassword(),
                manufacturerDTO.getName(),
                manufacturerDTO.getEmail(),
                manufacturerDTO.getRole()
        );
        return Response.ok().build();
    }

    @DELETE
    @Path("{username}")
    public Response deleteManufacturer(@PathParam("username") String username) {
        manufacturerBean.delete(username);
        return Response.ok().build();
    }
}
