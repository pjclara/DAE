package pt.ipleiria.estg.dei.ei.dae.backend.ws;

import jakarta.ejb.EJB;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pt.ipleiria.estg.dei.ei.dae.backend.dtos.PackageDTO;
import pt.ipleiria.estg.dei.ei.dae.backend.dtos.SensorDTO;
import pt.ipleiria.estg.dei.ei.dae.backend.ejbs.PackageBean;
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

    @GET
    @Path("/")
    public List<PackageDTO> getAllProducts() {
        return toDTOs(packageBean.all());
    }

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
    }

    @POST
    @Path("/")
    //@Authenticated
    //@RolesAllowed({"...Something..."})
    public Response createNewPackage (PackageDTO packageDTO) throws MyConstraintViolationException {

        packageBean.create(
                packageDTO.getId(),
                packageDTO.getPackagingType(),
                packageDTO.getPackagingMaterial()
                //packageDTO.getSensors()
        );
        Package newPackage = packageBean.find(packageDTO.getId());
        return Response.status(Response.Status.CREATED).entity(toDTO(newPackage)).build();
    }

    @PUT
    @Path("{id}")
    public Response updatePackage(@PathParam("id") Long id, PackageDTO packageDTO)
            throws MyEntityNotFoundException {

        try {
            // Retrieve the existing package
            Package existingPackage = packageBean.find(id);

            // Update package information
            existingPackage.setPackagingType(packageDTO.getPackagingType());
            existingPackage.setPackagingMaterial(packageDTO.getPackagingMaterial());

            // Update sensors
            List<SensorDTO> updatedSensors = packageDTO.getSensors();
            if (updatedSensors != null) {
                List<Sensor> sensors = updatedSensors.stream()
                        .map(sensorDTO -> new Sensor(/* map SensorDTO to Sensor */))
                        .collect(Collectors.toList());

                //existingPackage.addSensor(sensors); // make a loop for save each sensor
            }

            // Update the package in the database
            //packageBean.update(existingPackage);
            packageBean.update(
                    existingPackage.getId(),
                    existingPackage.getPackagingType(),
                    existingPackage.getPackagingMaterial()
                    //existingPackage.getSensors()
            );

            // Return the updated package as a response
            return Response.status(Response.Status.OK).entity(toDTO(existingPackage)).build();
        } catch (MyEntityNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("ERROR_FINDING_PACKAGE")
                    .build();
        }

        /*
        // incomplato, falta o update dos sensores
        packageBean.update(
                packageDTO.getId(),
                packageDTO.getPackagingType(),
                packageDTO.getPackagingMaterial()
                // packageDTO.getSensors()
        );
        Package package_ = packageBean.find(id);
        return Response.status(Response.Status.OK).entity(toDTO(package_)).build(); */
    }

    @DELETE
    @Path("{id}")
    public Response delete(@PathParam("id") Long packagingId) throws MyEntityNotFoundException {
        packageBean.remove(packagingId);
        return Response.ok().build();
    }

    private List<PackageDTO> toDTOs(List<Package> packages) {
        return packages.stream().map(this::toDTO).collect(Collectors.toList());
    }

    private PackageDTO toDTO(Package package_) { // o nome esta como 'package_' pois dava conflito sem o '_'
        return new PackageDTO(
                package_.getId(),
                package_.getPackagingType(),
                package_.getPackagingMaterial()
                //package_.getSensors()
        );
    }

}
