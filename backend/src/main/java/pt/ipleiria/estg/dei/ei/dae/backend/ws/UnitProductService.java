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
    private UnitProductBean unitProductsBean;

    @GET
    @Path("/")
    public List<UnitProductDTO> getAllUnitProducts() {
        return toDTOs(unitProductsBean.all());
    }

    @GET
    @Path("{id}")
    public Response get(@PathParam("id") Long unitProductId) {
        UnitProduct unitProduct = unitProductsBean.find(unitProductId);
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
                packageSensorToDTO(unitProduct.getPackageSensor() == null ? null : unitProduct.getPackageSensor())
        );
    }

    private PackageSensorDTO packageSensorToDTO(PackageSensor packageSensor) {
        if (packageSensor != null)
            return new PackageSensorDTO(
                packageSensor.getId(),
                sensorValueDTOs(packageSensor.getSensorValues() == null ? null : packageSensor.getSensorValues()),
                packageDTO(packageSensor.getPackagging() == null ? null : packageSensor.getPackagging()));
        else
            return new PackageSensorDTO();
    }

    private List<SensorValueDTO> sensorValueDTOs(List<SensorValue> sensorValues) {
        return sensorValues.stream().map(this::sensorValueDTO).collect(Collectors.toList());
    }

    private SensorValueDTO sensorValueDTO(SensorValue sensorValue) {
        return new SensorValueDTO(
                sensorValue.getId(),
                SensorDTO.toDTO(sensorValue.getSensor()),
                sensorValue.getValue()
        );
    }

    private PackageDTO packageDTO(Package aPackage) {
        return new PackageDTO(
                aPackage.getId(),
                aPackage.getPackagingType(),
                aPackage.getPackagingMaterial()
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