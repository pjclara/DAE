package pt.ipleiria.estg.dei.ei.dae.backend.ws;

import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pt.ipleiria.estg.dei.ei.dae.backend.dtos.*;
import pt.ipleiria.estg.dei.ei.dae.backend.ejbs.UnitProductBean;

import pt.ipleiria.estg.dei.ei.dae.backend.entities.*;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.Package;

import java.util.List;
import java.util.stream.Collectors;

@Path("/unitProducts")
@Produces({MediaType.APPLICATION_JSON}) // injects header “Content-Type: application/json”
@Consumes({MediaType.APPLICATION_JSON}) // injects header “Accept: application/json”
public class UnitProductService {

    @EJB
    private UnitProductBean un;

    @GET
    @Path("/")
    public List<UnitProductDTO> getAllUnitProducts() {
        return toDTOs(un.all());
    }

    @GET
    @Path("{id}")
    public Response get(@PathParam("id") Long unitProductId) {
        UnitProduct unitProduct = un.find(unitProductId);
        if (unitProduct != null) {
            var unitProductDTO = toDTO(unitProduct);
            return Response.ok(unitProductDTO).build();
        }
        return Response.status(Response.Status.NOT_FOUND)
                .entity("ERROR_FINDING_UNIT_PRODUCT")
                .build();
    }

    private List<UnitProductDTO> toDTOs(List<UnitProduct> all) {
        return all.stream().map(this::toDTO).collect(Collectors.toList());
    }

    private UnitProductDTO toDTO(UnitProduct unitProduct) {
        return new UnitProductDTO(
                unitProduct.getId(),
                unitProduct.getSerialNumber(),
                unitProduct.getAvailable(),
                productToDTO(unitProduct.getProduct() == null ? new Product() : unitProduct.getProduct()),
                packageSensorToDTO(unitProduct.getPackageSensor() == null ? new PackageSensor() : unitProduct.getPackageSensor())
        );
    }

    private PackageSensorDTO packageSensorToDTO(PackageSensor packageSensor) {
        return new PackageSensorDTO(
                packageSensor.getId(),
                (List<SensorDTO>) sensorDTOs(packageSensor.getSensors())
        );
    }

    private List<SensorDTO> sensorDTOs(List<Sensor> sensors) {
        return sensors.stream().map(this::sensorDTO).collect(Collectors.toList());
    }

    private PackageDTO packageDTO(Package aPackage) {
        return new PackageDTO(
        );
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
                sensor.getTimestamp()
        );
    }

    private ProductDTO productToDTO(Product product) {
        return new ProductDTO(
                product.getId(),
                product.getName(),
                product.getStock(),
                product.getImage(),
                product.getManufacturer().getUsername()
        );
    }


}
