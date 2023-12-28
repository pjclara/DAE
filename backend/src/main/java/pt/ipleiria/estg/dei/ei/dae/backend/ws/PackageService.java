package pt.ipleiria.estg.dei.ei.dae.backend.ws;

import jakarta.ejb.EJB;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import org.hibernate.Hibernate;
import pt.ipleiria.estg.dei.ei.dae.backend.dtos.PackageDTO;
import pt.ipleiria.estg.dei.ei.dae.backend.dtos.SensorDTO;
import pt.ipleiria.estg.dei.ei.dae.backend.ejbs.PackageBean;
import pt.ipleiria.estg.dei.ei.dae.backend.ejbs.SensorBean;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.Package;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.Sensor;
import pt.ipleiria.estg.dei.ei.dae.backend.exceptions.MyConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.backend.exceptions.MyEntityNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Path("/packages")
@Produces({MediaType.APPLICATION_JSON}) // injects header “Content-Type: application/json”
@Consumes({MediaType.APPLICATION_JSON}) // injects header “Accept: application/json”
public class PackageService {

    @EJB
    private PackageBean packageBean;

    @EJB
    private SensorBean sensorBean;

    @GET
    @Path("/")
    public List<PackageDTO> getAllProducts() {
        List<Package> packages = packageBean.all();
        return PackageDTO.from(packages);
    }


    /*
    @GET
    @Path("{id}")
    public Response get(@PathParam("id") Long packageId) {
        Package package_ = packageBean.find(packageId);
        if (package_ != null) {
            return Response.ok(toDTO(package_)).build();
        }
        return Response.status(Response.Status.NOT_FOUND)
                .entity("ERROR_FINDING_PRODUCT")
                .build();
    } */

    @GET
    @Path("{id}")
    public Response get(@PathParam("id") Long packageId) {
        Package package_ = packageBean.find(packageId);
        if (package_ != null) {
            return Response.ok(PackageDTO.from(package_)).build();
        }
        return Response.status(Response.Status.NOT_FOUND)
                .entity("ERROR_FINDING_PACKAGE")
                .build();
    }

    @GET
    @Path("{id}/sensors")
    public Response getPackageSensors(@PathParam("id") Long packageId) throws MyEntityNotFoundException {
        Package package_ = packageBean.findOrFail(packageId);
        var sensors = package_.getSensors();

        if (sensors == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("ERROR_FINDING_PACKAGE_SENSORS").build();
        }

        Hibernate.initialize(sensors);
        return Response.ok(SensorDTO.from(sensors)).build();
    }

    @POST
    @Path("/")
    //@Authenticated
    //@RolesAllowed({"...Something..."})
    public Response createNewPackage(PackageDTO packageDTO) throws MyConstraintViolationException {

        packageBean.create(
                packageDTO.getId(),
                packageDTO.getPackagingType(),
                packageDTO.getPackagingMaterial()
        );
        Package newPackage = packageBean.find(packageDTO.getId());
        return Response.status(Response.Status.CREATED).entity(PackageDTO.from(newPackage)).build();
    }

    /*
    @PUT
    @Path("{id}")
    public Response updatePackage(@PathParam("id") Long id, PackageDTO packageDTO)
            throws MyEntityNotFoundException {

        packageBean.update(
                packageDTO.getId(),
                packageDTO.getPackagingType(),
                packageDTO.getPackagingMaterial(),
                packageDTO.getSensors()
                //sensors_toDTOs(packageDTO.getSensors())
        );
        Package package_ = packageBean.find(id);
        return Response.status(Response.Status.OK).entity(toDTO(package_)).build();
    }*/
    @PUT
    @Path("{id}")
    public Response updatePackage(@PathParam("id") Long id, PackageDTO packageDTO)
            throws MyEntityNotFoundException {
        packageBean.update(
                packageDTO.getId(),
                packageDTO.getPackagingType(),
                packageDTO.getPackagingMaterial()
        );
        Package package_ = packageBean.find(id);
        return Response.status(Response.Status.OK).entity(PackageDTO.from(package_)).build();
    }

    // Add and Remove a Sensor of a package
    @POST
    @Path("{id}/sensor/{sensorId}")
    public Response addSensorToPackage(@PathParam("id") Long id, @PathParam("sensorId") Long sensorId)
            throws MyEntityNotFoundException {

        packageBean.addSensorToPackage(id, sensorId);
        return Response.ok().build();
    }
    @DELETE
    @Path("{id}/sensor/{sensorId}")
    public Response deleteSensorOfPackage(@PathParam("id") Long id, @PathParam("sensorId") Long sensorId)
            throws MyEntityNotFoundException {
        packageBean.removeSensorFromPackage(id, sensorId);
        return Response.ok().build();
    }

    @DELETE
    @Path("{id}")
    public Response delete(@PathParam("id") Long packagingId) throws MyEntityNotFoundException {
        packageBean.remove(packagingId);
        return Response.ok().build();
    }
/*
    private List<PackageDTO> toDTOs(List<Package> packages) {
        return packages.stream().map(this::toDTO).collect(Collectors.toList());
    }

    private PackageDTO toDTO(Package package_) { // o nome esta como 'package_' pois dava conflito sem o '_'
        return new PackageDTO(
                package_.getId(),
                package_.getPackagingType(),
                package_.getPackagingMaterial(),
                sensors_toDTOs(package_.getSensors())
        );
    }

    private SensorDTO sensors_toDTO(Sensor sensor) {
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
    private List<SensorDTO> sensors_toDTOs(List<Sensor> sensors) {
        return sensors.stream().map(this::sensors_toDTO).collect(Collectors.toList());
    }

 */
}
