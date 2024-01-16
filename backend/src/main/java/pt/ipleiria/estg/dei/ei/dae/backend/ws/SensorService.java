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
        return SensorDTO.toDTOs(sensorBean.getAll());
    }

    @GET
    @Path("{id}")
    public Response getSensorDetails(@PathParam("id") Long id) throws MyEntityNotFoundException {
        Sensor sensor = sensorBean.find(id);
        if (sensor != null) {
            return Response.ok(SensorDTO.toDTO(sensor)).build();
        }
        return Response.status(Response.Status.NOT_FOUND)
                .entity("ERROR_FINDING_SENSOR")
                .build();
    }

    @POST
    @Path("/")
    public Response createNewSensor(SensorDTO sensorDTO) throws MyConstraintViolationException {
        long id = sensorBean.create(
                sensorDTO.getSource(),
                sensorDTO.getType(),
                sensorDTO.getUnit(),
                sensorDTO.getMax(),
                sensorDTO.getMin()
        );
        Sensor sensor = sensorBean.find(id);
        if (sensor == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        return Response.status(Response.Status.CREATED).entity(SensorDTO.toDTO(sensor)).build();
    }

    @PUT
    @Path("{id}")
    public Response updateSensor(@PathParam("id") long id, SensorDTO sensorDTO) throws MyEntityNotFoundException, MyConstraintViolationException {
        sensorBean.update(
                id,
                sensorDTO.getSource(),
                sensorDTO.getType(),
                sensorDTO.getUnit(),
                sensorDTO.getMax(),
                sensorDTO.getMin()
        );
        Sensor sensor = sensorBean.find(id);
        if (sensor == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.status(Response.Status.CREATED).entity(SensorDTO.toDTO(sensor)).build();
    }

    @DELETE
    @Path("{id}")
    public Response deleteSensor(@PathParam("id") Long id) throws MyEntityNotFoundException{
        sensorBean.delete(id);
        return Response.ok().build();
    }
}
