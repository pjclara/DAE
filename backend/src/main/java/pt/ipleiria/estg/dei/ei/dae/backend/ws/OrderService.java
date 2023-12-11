package pt.ipleiria.estg.dei.ei.dae.backend.ws;

import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pt.ipleiria.estg.dei.ei.dae.backend.dtos.OrderDTO;
import pt.ipleiria.estg.dei.ei.dae.backend.ejbs.OrderBean;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.Orderr;
import pt.ipleiria.estg.dei.ei.dae.backend.exceptions.MyConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.backend.exceptions.MyEntityNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Path("/orders")
@Produces({MediaType.APPLICATION_JSON}) // injects header “Content-Type: application/json”
@Consumes({MediaType.APPLICATION_JSON}) // injects header “Accept: application/json”
public class OrderService {

    @EJB
    private OrderBean orderBean;

    @GET
    @Path("/")
    public List<OrderDTO> getAllOrders() {
        return toDTOs(orderBean.all());
    }


    @POST
    @Path("/")
    public Response createNewOrder(OrderDTO orderDTO)
            throws MyEntityNotFoundException, MyConstraintViolationException {
        orderBean.create(
                orderDTO.getId(),
                orderDTO.getStatus(),
                orderDTO.getLogisticsOperatorName(),
                orderDTO.getEndConsumerName()
        );

        Orderr order = orderBean.findOrFail(orderDTO.getId());
        if (order == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        return Response.status(Response.Status.CREATED).entity(toDo(order)).build();
    }

    @GET
    @Path("{id}")
    public Response getEndConsumerDetails(@PathParam("id") Long orderId) throws MyEntityNotFoundException {
        Orderr order = orderBean.findOrFail(orderId);
        if (order != null) {
            return Response.ok(toDTO(order)).entity(toDTO(order)).build();
        }
        return Response.status(Response.Status.NOT_FOUND)
                .entity("ERROR_FINDING_ORDER")
                .build();
    }

    @PUT
    @Path("{id}")
    public Response updateOrder(@PathParam("id") Long id, OrderDTO orderDTO)
    throws MyEntityNotFoundException {
        orderBean.update(
                orderDTO.getId(),
                orderDTO.getStatus(),
                orderDTO.getLogisticsOperatorName(),
                orderDTO.getEndConsumerName()
        );
        Orderr order = orderBean.findOrFail(id);
        if (order == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.status(Response.Status.OK).entity(toDTO(order)).build();
    }

    @DELETE
    @Path("{id}")
    public Response deleteEndConsumer(@PathParam("id") Long orderId) throws MyEntityNotFoundException {
        orderBean.delete(orderId);
        return Response.ok().build();
    }

    // -----------------------------------------------------
    private List<OrderDTO> toDTOs(List<Orderr> orders) {
        return orders.stream().map(this::toDTO).collect(Collectors.toList());
    }

    private OrderDTO toDTO(Orderr order) {
        return new OrderDTO(
                order.getId(),
                order.getStatus(),
                order.getEndConsumer().getName(),
                order.getLogisticsOperators().getName()
        );
    }

    private OrderDTO toDo(Orderr order) {
        return new OrderDTO(
                order.getId(),
                order.getStatus(),
                order.getEndConsumer().getName(),
                order.getLogisticsOperators().getName()
        );
    }

}
