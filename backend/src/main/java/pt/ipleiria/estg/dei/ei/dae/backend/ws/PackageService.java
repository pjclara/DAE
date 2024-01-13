package pt.ipleiria.estg.dei.ei.dae.backend.ws;

import jakarta.annotation.security.RolesAllowed;
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
import pt.ipleiria.estg.dei.ei.dae.backend.ejbs.UserBean;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.Package;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.PackagingType;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.Sensor;
import pt.ipleiria.estg.dei.ei.dae.backend.exceptions.MyConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.backend.exceptions.MyEntityNotFoundException;
import pt.ipleiria.estg.dei.ei.dae.backend.security.Authenticated;

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

    @EJB
    private UserBean userBean;

    @Context
    private SecurityContext securityContext;

    @GET
    @Authenticated
    @Path("/")
    public List<PackageDTO> getAllPackages() {
        // Get the user's role from the security context
        //String userRole = getUserRole();

        // Getting the appropriate list of packages based on the role
       // List<Package> packages = packageBean.getAllPackagesByRole(userRole);
        //return PackageDTO.from(packages);
        return toDTOs(packageBean.all());
    }

    private List<PackageDTO> toDTOs(List<Package> all) {
        return all.stream().map(PackageDTO::from).collect(Collectors.toList());
    }

    @GET
    @Path("{id}")
    public Response get(@PathParam("id") Long packageId) {
           Package package_ = packageBean.find(packageId);

            if (package_ != null) {
                var packageDTO = PackageDTO.from(package_);
                return Response.ok(packageDTO).build();
            }
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("ERROR_FINDING_PACKAGE")
                    .build();

    }

    @GET
    @Path("{id}/sensors")
    public Response getPackageSensors(@PathParam("id") Long packageId) throws MyEntityNotFoundException {
        Package package_ = packageBean.findOrFail(packageId);
        List<Sensor> sensors = sensorBean.getSensorsByPackage(package_);

        if (sensors == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("ERROR_FINDING_PACKAGE_SENSORS").build();
        }

        Hibernate.initialize(sensors);
        return Response.ok(SensorDTO.from(sensors)).build();
    }

    @POST
    @Path("/")
    //@Authenticated
    //@RolesAllowed({"Admin"})
    public Response createNewPackage(PackageDTO packageDTO) throws MyConstraintViolationException {
        long id = packageBean.create(
                packageDTO.getPackagingType(),
                packageDTO.getPackagingMaterial()
        );
        Package newPackage = packageBean.find(id);
        return Response.status(Response.Status.CREATED).entity(PackageDTO.from(newPackage)).build();
    }

    @PUT
    @Path("{id}")
    //@Authenticated
    //@RolesAllowed({"Admin"})
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
    //@Authenticated
    //@RolesAllowed({"Manufacturer", "LogisticsOperator"})
    @Path("{id}/sensor/{sensorId}")
    public Response addSensorToPackage(@PathParam("id") Long id, @PathParam("sensorId") Long sensorId)
            throws MyEntityNotFoundException {

        if (!securityContext.isUserInRole("Manufacturer") || !securityContext.isUserInRole("LogisticsOperator")){
            return Response.status(Response.Status.FORBIDDEN).build();
        }

        return Response.ok().build();
    }

    @DELETE
    //@Authenticated
    //@RolesAllowed({"Manufacturer", "LogisticsOperator"})
    @Path("{id}/sensor/{sensorId}")
    public Response deleteSensorOfPackage(@PathParam("id") Long id, @PathParam("sensorId") Long sensorId)
            throws MyEntityNotFoundException {

        if (!securityContext.isUserInRole("Manufacturer") || !securityContext.isUserInRole("LogisticsOperator")){
            return Response.status(Response.Status.FORBIDDEN).build();
        }

        packageBean.removeSensorFromPackage(id, sensorId);
        return Response.ok().build();
    }
    // ------------

    @DELETE
    @Path("{id}")
    //@Authenticated
    //@RolesAllowed({"Admin"})
    public Response delete(@PathParam("id") Long packagingId) throws MyEntityNotFoundException {
        packageBean.remove(packagingId);
        return Response.ok().build();
    }

    // Helper method to get the user's role from the security context
    private String getUserRole() {
        var username = securityContext.getUserPrincipal().getName();
        String role = userBean.findOrFail(username).getRole();
        return role;
    }


    // get package by type
    @GET
    @Path("/packagingType/{packagingType}")
    public List<PackageDTO> getPackageByType(@PathParam("packagingType") PackagingType packagingTypeId) throws MyEntityNotFoundException {
        List<Package> packages = packageBean.getPackageByType(packagingTypeId);
        return PackageDTO.from(packages);
    }
}
