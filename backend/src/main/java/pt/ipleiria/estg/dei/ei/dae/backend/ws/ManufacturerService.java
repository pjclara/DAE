package pt.ipleiria.estg.dei.ei.dae.backend.ws;

import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pt.ipleiria.estg.dei.ei.dae.backend.dtos.ManufacturerDTO;
import pt.ipleiria.estg.dei.ei.dae.backend.dtos.ProductDTO;
import pt.ipleiria.estg.dei.ei.dae.backend.ejbs.ManufacturerBean;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.Manufacturer;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.Product;
import pt.ipleiria.estg.dei.ei.dae.backend.exceptions.MyEntityNotFoundException;

import java.util.List;

@Path("/manufacturers")
@Produces({MediaType.APPLICATION_JSON}) // injects header “Content-Type: application/json”
@Consumes({MediaType.APPLICATION_JSON}) // injects header “Accept: application/json”
public class ManufacturerService {

    @EJB
    private ManufacturerBean manufacturerBean;

    @GET
    @Path("/")
    public List<ManufacturerDTO> getAllManufacturers() { return toDTOs(manufacturerBean.all());   }

    @GET
    @Path("{username}")
    public Response getManufacturerDetails(@PathParam("username") String username) throws MyEntityNotFoundException {
        Manufacturer manufacturer = manufacturerBean.find(username);
        if (manufacturer != null) {
            return Response.ok(toDTO(manufacturer)).entity(toDTO(manufacturer)).build();
        }
        return Response.status(Response.Status.NOT_FOUND)
                .entity("ERROR_FINDING_MANUFACTURER")
                .build();
    }

    @POST
    @Path("/")
    public Response createNewManufacturer(ManufacturerDTO manufacturerDTO) throws MyEntityNotFoundException {
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

    @PUT
    @Path("{username}")
    public Response updateManufacturer(@PathParam("username") String username, ManufacturerDTO manufacturerDTO) throws MyEntityNotFoundException {
        manufacturerBean.update(
                manufacturerDTO.getUsername(),
                manufacturerDTO.getPassword(),
                manufacturerDTO.getName(),
                manufacturerDTO.getEmail(),
                manufacturerDTO.getRole()
        );
        Manufacturer manufacturer = manufacturerBean.find(username);
        return Response.status(Response.Status.OK).entity(toDTO(manufacturer)).build();
    }

    @DELETE
    @Path("{username}")
    public Response deleteManufacturer(@PathParam("username") String username) throws MyEntityNotFoundException {
        manufacturerBean.delete(username);
        return Response.ok().build();
    }


    // get all products from manufacturer
    @GET
    @Path("{username}/products")
    public Response getAllProductsFromManufacturer(@PathParam("username") String username) {
        Manufacturer manufacturer = manufacturerBean.getAllProductsFromManufacturer(username);
        if (manufacturer != null) {
            var dtos = productsToDo(manufacturer.getProducts());
            return Response.ok(dtos).build();
        }
        return Response.status(Response.Status.NOT_FOUND)
                .entity("ERROR_FINDING_MANUFACTURER")
                .build();
    }

    // get product by id
    @GET
    @Path("{username}/products/{id}")
    public Response getProductDetails(@PathParam("username") String username, @PathParam("id") Long id) throws MyEntityNotFoundException {
        Product product = manufacturerBean.getProductDetails(username, id);
        if (product != null) {
            return Response.ok(productToDo(product)).build();
        }
        return Response.status(Response.Status.NOT_FOUND)
                .entity("ERROR_FINDING_PRODUCT")
                .build();
    }

    // AUXILIARY FUNCTIONS

    private ManufacturerDTO toDo(Manufacturer manufacturer) {
        return new ManufacturerDTO(
                manufacturer.getUsername(),
                manufacturer.getPassword(),
                manufacturer.getName(),
                manufacturer.getEmail(),
                manufacturer.getRole()
        );
    }
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

    private List<ProductDTO> productsToDo(List<Product> products) {
        return products.stream().map(this::productToDo).collect(java.util.stream.Collectors.toList());
    }

    private ProductDTO productToDo(Product product) {
        return new ProductDTO(
                product.getId(),
                product.getName(),
                product.getStock(),
                product.getImage(),
                product.getManufacturer().getUsername(),
                product.getProductPackage() == null ? null:product.getProductPackage().getId()
        );
    }
    // get all products from manufacturer

}
