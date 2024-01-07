package pt.ipleiria.estg.dei.ei.dae.backend.ws;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import pt.ipleiria.estg.dei.ei.dae.backend.ejbs.SensorBean;
import pt.ipleiria.estg.dei.ei.dae.backend.dtos.SensorDTO;

import jakarta.ejb.EJB;
import jakarta.ws.rs.core.MediaType;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.Sensor;
import pt.ipleiria.estg.dei.ei.dae.backend.exceptions.MyConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.backend.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.dei.ei.dae.backend.exceptions.MyEntityNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Path("/sensors")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class SensorService {
    @EJB
    private SensorBean sensorBean;

    @GET
    @Path("/")
    public List<SensorDTO> getAllSensors() {
        return toDTOs(sensorBean.getAll());
    }
    private List<SensorDTO> toDTOs(List<Sensor> sensors) {
        return sensors.stream().map(this::toDTO).collect(java.util.stream.Collectors.toList());
    }
    private SensorDTO toDTO(Sensor sensor) {
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

    @GET
    @Path("{id}")
    public Response getSensorDetails(@PathParam("id") Long id) throws MyEntityNotFoundException {
        Sensor sensor = sensorBean.find(id);
        if (sensor != null) {
            return Response.ok(toDTO(sensor)).build();
        }
        return Response.status(Response.Status.NOT_FOUND)
                .entity("ERROR_FINDING_SENSOR")
                .build();
    }

    @POST
    @Path("/")
    public Response createNewSensor(SensorDTO sensorDTO) throws MyEntityNotFoundException, MyEntityExistsException, MyConstraintViolationException {
        long id = sensorBean.create(
                sensorDTO.getSource(),
                sensorDTO.getType(),
                sensorDTO.getValue(),
                sensorDTO.getUnit(),
                sensorDTO.getMax(),
                sensorDTO.getMin(),
                sensorDTO.getTimestamp()
        );
        Sensor sensor = sensorBean.find(id);
        if (sensor == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        return Response.status(Response.Status.CREATED).entity(toDTO(sensor)).build();
    }

    @PUT
    @Path("{id}")
    public Response updateSensor(@PathParam("id") long id, SensorDTO sensorDTO) throws MyEntityNotFoundException, MyConstraintViolationException {
        sensorBean.update(
                sensorDTO.getId(),
                sensorDTO.getSource(),
                sensorDTO.getType(),
                sensorDTO.getValue(),
                sensorDTO.getUnit(),
                sensorDTO.getMax(),
                sensorDTO.getMin(),
                sensorDTO.getTimestamp(),
                sensorDTO.getPackageId()
        );
        Sensor sensor = sensorBean.find(id);
        if (sensor == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.status(Response.Status.CREATED).entity(toDTO(sensor)).build();
    }

    @DELETE
    @Path("{id}")
    public Response deleteSensor(@PathParam("id") Long id) throws MyEntityNotFoundException{
        sensorBean.delete(id);
        return Response.ok().build();
    }
}
