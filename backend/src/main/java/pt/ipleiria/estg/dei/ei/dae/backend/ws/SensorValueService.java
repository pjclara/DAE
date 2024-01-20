package pt.ipleiria.estg.dei.ei.dae.backend.ws;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import pt.ipleiria.estg.dei.ei.dae.backend.dtos.SensorValueDTO;
import pt.ipleiria.estg.dei.ei.dae.backend.ejbs.SensorBean;
import pt.ipleiria.estg.dei.ei.dae.backend.dtos.SensorDTO;

import jakarta.ejb.EJB;
import jakarta.ws.rs.core.MediaType;
import pt.ipleiria.estg.dei.ei.dae.backend.ejbs.SensorValueBean;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.Sensor;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.SensorValue;
import pt.ipleiria.estg.dei.ei.dae.backend.exceptions.MyConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.backend.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.dei.ei.dae.backend.exceptions.MyEntityNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Path("/sensors")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class SensorValueService {
    @EJB
    private SensorValueBean sensorValueBean;

    @PUT
    @Path("{id}/{value}")
    public Response updateSensor(@PathParam("id") long id, @PathParam("value") String value) throws MyEntityNotFoundException, MyConstraintViolationException {
        sensorValueBean.update(
                id,
                value
        );
        SensorValue sensorValue = sensorValueBean.find(id);
        if (sensorValue == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.status(Response.Status.CREATED).entity(SensorValueDTO.toDTO(sensorValue)).build();
    }
}
