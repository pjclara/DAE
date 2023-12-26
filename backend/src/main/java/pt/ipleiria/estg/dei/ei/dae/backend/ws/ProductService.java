package pt.ipleiria.estg.dei.ei.dae.backend.ws;

import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.glassfish.jaxb.core.v2.TODO;
import pt.ipleiria.estg.dei.ei.dae.backend.dtos.EndConsumerDTO;
import pt.ipleiria.estg.dei.ei.dae.backend.dtos.ProductDTO;
import pt.ipleiria.estg.dei.ei.dae.backend.dtos.SensorDTO;
import pt.ipleiria.estg.dei.ei.dae.backend.ejbs.ProductBean;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.EndConsumer;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.Product;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.Sensor;
import pt.ipleiria.estg.dei.ei.dae.backend.exceptions.MyConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.backend.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.dei.ei.dae.backend.exceptions.MyEntityNotFoundException;

import java.util.List;

@Path("/products")
@Produces({MediaType.APPLICATION_JSON}) // injects header “Content-Type: application/json”
@Consumes({MediaType.APPLICATION_JSON}) // injects header “Accept: application/json”
public class ProductService {

    @EJB
    private ProductBean productBean;

    // get all products
    @GET
    @Path("/")
    public List<ProductDTO> getAllProducts() {
        return toDTOs(productBean.all());
    }


    // get product by id
    @GET
    @Path("{id}")
    public Response getProductDetails(@PathParam("id") Long id) throws MyEntityNotFoundException {
        Product product = productBean.find(id);
        if (product != null) {
            return Response.ok(toDTO(product)).build();
        }
        return Response.status(Response.Status.NOT_FOUND)
                .entity("ERROR_FINDING_PRODUCT")
                .build();
    }

    // create new product
    @POST
    @Path("/")
    public Response createNewProduct(ProductDTO productDTO) throws MyEntityNotFoundException, MyEntityExistsException, MyConstraintViolationException {
        productBean.create(
                productDTO.getId(),
                productDTO.getName(),
                productDTO.getStock(),
                productDTO.getImage(),
                productDTO.getManufacturerUsername()
        );
        Product product = productBean.find(productDTO.getId());
        if (product == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        return Response.status(Response.Status.CREATED).entity(toDo(product)).build();
    }
    // update product
    @PUT
    @Path("{id}")
    public Response updateProduct(@PathParam("id") Long id, ProductDTO productDTO)
    throws MyEntityNotFoundException {
        productBean.update(
                productDTO.getId(),
                productDTO.getName(),
                productDTO.getStock(),
                productDTO.getManufacturerUsername()
        );
        Product product = productBean.find(id);
        if (product == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.status(Response.Status.CREATED).entity(toDo(product)).build();
    }
    // delete product
    @DELETE
    @Path("{id}")
    public Response deleteProduct(@PathParam("id") Long id) throws MyEntityNotFoundException{
        productBean.delete(id);
        return Response.ok().build();
    }
    private List<ProductDTO> toDTOs(List<Product> all) {
        return all.stream().map(this::toDTO).collect(java.util.stream.Collectors.toList());
    }

    private ProductDTO toDTO(Product product) {
        return new ProductDTO(
                product.getId(),
                product.getName(),
                product.getStock(),
                product.getManufacturer().getUsername()
        );
    }

    private ProductDTO toDo(Product product) {
        return new ProductDTO(
                product.getId(),
                product.getName(),
                product.getStock(),
                product.getManufacturer().getUsername()
        );
    }


}
