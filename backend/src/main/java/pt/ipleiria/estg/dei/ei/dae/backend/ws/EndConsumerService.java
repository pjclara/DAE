package pt.ipleiria.estg.dei.ei.dae.backend.ws;

import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pt.ipleiria.estg.dei.ei.dae.backend.dtos.EndConsumerDTO;
import pt.ipleiria.estg.dei.ei.dae.backend.ejbs.EndConsumerBean;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.EndConsumer;

import java.util.List;

@Path("/endconsumers")
@Produces({MediaType.APPLICATION_JSON}) // injects header “Content-Type: application/json”
@Consumes({MediaType.APPLICATION_JSON}) // injects header “Accept: application/json”
public class EndConsumerService {

    @EJB
    private EndConsumerBean endConsumerBean;

    @GET
    @Path("/")
    public List<EndConsumerDTO> getAllEndConsumers() {
        return toDTOs(endConsumerBean.all());
    }

    private List<EndConsumerDTO> toDTOs(List<EndConsumer> all) {
        return all.stream().map(this::toDTO).collect(java.util.stream.Collectors.toList());
    }

    private EndConsumerDTO toDTO(EndConsumer endConsumer) {
        return new EndConsumerDTO(
                endConsumer.getUsername(),
                endConsumer.getPassword(),
                endConsumer.getName(),
                endConsumer.getEmail(),
                endConsumer.getRole()
        );
    }

    @POST
    @Path("/")
    public Response createNewEndConsumer(EndConsumerDTO endConsumerDTO) {
        endConsumerBean.create(
                endConsumerDTO.getUsername(),
                endConsumerDTO.getPassword(),
                endConsumerDTO.getName(),
                endConsumerDTO.getEmail(),
                endConsumerDTO.getRole()
        );
        EndConsumer endConsumer = endConsumerBean.find(endConsumerDTO.getUsername());
        if (endConsumer == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        return Response.status(Response.Status.CREATED).entity(toDo(endConsumer)).build();
    }

    private EndConsumerDTO toDo(EndConsumer endConsumer) {
        return new EndConsumerDTO(
                endConsumer.getUsername(),
                endConsumer.getPassword(),
                endConsumer.getName(),
                endConsumer.getEmail(),
                endConsumer.getRole()
        );
    }
    @GET
    @Path("{username}")
    public Response getEndConsumerDetails(@PathParam("username") String username) {
        EndConsumer endConsumer = endConsumerBean.find(username);
        if (endConsumer != null) {
            return Response.ok(toDTO(endConsumer)).entity(toDTO(endConsumer)).build();
        }
        return Response.status(Response.Status.NOT_FOUND)
                .entity("ERROR_FINDING_ENDCONSUMER")
                .build();
    }

    @PUT
    @Path("{username}")
    public Response updateEndConsumer(@PathParam("username") String username, EndConsumerDTO endConsumerDTO) {
        var endConsumer = endConsumerBean.find(username);
        if (endConsumer == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        endConsumerBean.update(
                endConsumerDTO.getUsername(),
                endConsumerDTO.getPassword(),
                endConsumerDTO.getName(),
                endConsumerDTO.getEmail(),
                endConsumerDTO.getRole()
        );
        return Response.ok().entity(toDTO(endConsumer)).build();
    }

    @DELETE
    @Path("{username}")
    public Response deleteEndConsumer(@PathParam("username") String username) {
        endConsumerBean.delete(username);
        return Response.ok().build();
    }

}
