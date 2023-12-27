package pt.ipleiria.estg.dei.ei.dae.backend.ws;

import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pt.ipleiria.estg.dei.ei.dae.backend.dtos.LogisticsOperatorDTO;
import pt.ipleiria.estg.dei.ei.dae.backend.ejbs.LogisticsOperatorBean;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.LogisticsOperator;

import java.util.List;

@Path("/logisticsOperators")
@Produces({MediaType.APPLICATION_JSON}) // injects header “Content-Type: application/json”
@Consumes({MediaType.APPLICATION_JSON}) // injects header “Accept: application/json”
public class LogisticsOperatorService {

    @EJB
    private LogisticsOperatorBean logisticsOperatorBean;

    @GET
    @Path("/")
    public List<LogisticsOperatorDTO> getAllLogisticsOperators() {
        return toDTOs(logisticsOperatorBean.all());
    }

    private List<LogisticsOperatorDTO> toDTOs(List<LogisticsOperator> all) {
        return all.stream().map(this::toDTO).collect(java.util.stream.Collectors.toList());
    }

    private LogisticsOperatorDTO toDTO(LogisticsOperator logisticsOperator) {
        return new LogisticsOperatorDTO(
                logisticsOperator.getUsername(),
                logisticsOperator.getPassword(),
                logisticsOperator.getName(),
                logisticsOperator.getEmail(),
                logisticsOperator.getRole()
        );
    }
    @GET
    @Path("{username}")
    public Response getLogisticsOperatorDetails(@PathParam("username") String username) {
        LogisticsOperator logisticsOperator = logisticsOperatorBean.find(username);
        if (logisticsOperator != null) {
            return Response.ok(toDTO(logisticsOperator)).entity(toDTO(logisticsOperator)).build();
        }
        return Response.status(Response.Status.NOT_FOUND)
                .entity("ERROR_FINDING_LOGISTICS_OPERATOR")
                .build();
    }

    @PUT
    @Path("{username}")
    public Response updateLogisticsOperator(@PathParam("username") String username, LogisticsOperatorDTO logisticsOperatorDTO) {
        logisticsOperatorBean.update(
                username,
                logisticsOperatorDTO.getPassword(),
                logisticsOperatorDTO.getName(),
                logisticsOperatorDTO.getEmail(),
                logisticsOperatorDTO.getRole()
        );
        LogisticsOperator logisticsOperator = logisticsOperatorBean.find(username);
        if (logisticsOperator != null) {
            return Response.status(Response.Status.OK).entity(toDTO(logisticsOperator)).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    @POST
    @Path("/")
    public Response createNewLogisticsOperator(LogisticsOperatorDTO logisticsOperatorDTO) {
        logisticsOperatorBean.create(
                logisticsOperatorDTO.getUsername(),
                logisticsOperatorDTO.getPassword(),
                logisticsOperatorDTO.getName(),
                logisticsOperatorDTO.getEmail(),
                logisticsOperatorDTO.getRole()
        );
        LogisticsOperator logisticsOperator = logisticsOperatorBean.find(logisticsOperatorDTO.getUsername());
        if (logisticsOperator == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        return Response.status(Response.Status.CREATED).entity(toDTO(logisticsOperator)).build();
    }

    @DELETE
    @Path("{username}")
    public Response deleteLogisticsOperator(@PathParam("username") String username) {
        logisticsOperatorBean.delete(username);
        return Response.ok().build();
    }

}
