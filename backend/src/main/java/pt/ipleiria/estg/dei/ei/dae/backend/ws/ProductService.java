package pt.ipleiria.estg.dei.ei.dae.backend.ws;

import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pt.ipleiria.estg.dei.ei.dae.backend.dtos.PackageDTO;
import pt.ipleiria.estg.dei.ei.dae.backend.dtos.ProductDTO;
import pt.ipleiria.estg.dei.ei.dae.backend.dtos.SensorDTO;
import pt.ipleiria.estg.dei.ei.dae.backend.ejbs.ProductBean;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.Package;
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
        Product product = productBean.findWithPackage(id);
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
        long id  = productBean.create(
                productDTO.getName(),
                productDTO.getStock(),
                productDTO.getImage(),
                productDTO.getManufacturerUsername()
        );
        Product product = productBean.find(id);
        if (product == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        return Response.status(Response.Status.CREATED).entity(toDTO(product)).build();
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
                productDTO.getManufacturerUsername(),
                productDTO.getImage()
        );
        Product product = productBean.find(id);
        if (product == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.status(Response.Status.CREATED).entity(toDTO(product)).build();
    }
    private ProductDTO toDTO(Product product) {
        return new ProductDTO(
                product.getId(),
                product.getName(),
                product.getStock(),
                product.getImage(),
                product.getManufacturer().getUsername()
        );
    }

    private PackageDTO packageDTO(Package productPackage) {
        return new PackageDTO(
                productPackage.getId(),
                productPackage.getPackagingType(),
                productPackage.getPackagingMaterial(),
                sensorsDTO(productPackage.getAllPackageSensors())
        );

    }

    private List<SensorDTO> sensorsDTO(List<Sensor> sensors) {
        return sensors.stream().map(this::sensorDTO).collect(java.util.stream.Collectors.toList());
    }

    private SensorDTO sensorDTO(Sensor sensor) {
        return new SensorDTO(
                sensor.getId(),
                sensor.getSource(),
                sensor.getType(),
                sensor.getValue(),
                sensor.getUnit(),
                sensor.getMax(),
                sensor.getMin(),
                sensor.getTimestamp(),
                sensor.getPackagging().getId()
        );
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



}
