package pt.ipleiria.estg.dei.ei.dae.backend.ws;

import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import pt.ipleiria.estg.dei.ei.dae.backend.dtos.OrderDTO;
import pt.ipleiria.estg.dei.ei.dae.backend.dtos.OrderItemDTO;
import pt.ipleiria.estg.dei.ei.dae.backend.dtos.PackageDTO;
import pt.ipleiria.estg.dei.ei.dae.backend.ejbs.OrderBean;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.OrderItem;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.Orderr;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.Package;
import pt.ipleiria.estg.dei.ei.dae.backend.exceptions.MyConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.backend.exceptions.MyEntityNotFoundException;
import pt.ipleiria.estg.dei.ei.dae.backend.security.Authenticated;

import java.util.List;
import java.util.stream.Collectors;

@Path("/orders")
@Produces({MediaType.APPLICATION_JSON}) // injects header “Content-Type: application/json”
@Consumes({MediaType.APPLICATION_JSON}) // injects header “Accept: application/json”
//@Authenticated
public class OrderService {

    @EJB
    private OrderBean orderBean;

    @Context
    private SecurityContext securityContext;

    @GET
    @Path("/")
    public List<OrderDTO> getAllOrders() {
        return toDTOs(orderBean.all());
    }
    /*
    @GET
    @Path("/")
    public List<OrderDTO> getAllOrdersByUser() { // get only the corresponding orders
        String endConsumerUsername = securityContext.getUserPrincipal().getName();
        return toDTOs(orderBean.getOrdersByEndConsumer(endConsumerUsername));
    } */

    @GET
    @Path("{id}")
    public Response get(@PathParam("id") Long orderId) throws MyEntityNotFoundException {
        Orderr order = orderBean.findOrFail(orderId);
        if (order != null) {
            return Response.ok(toDTO(order)).entity(toDTO(order)).build();
        }
        return Response.status(Response.Status.NOT_FOUND)
                .entity("ERROR_FINDING_ORDER")
                .build();
    }
/*
    @GET
    @Path("{id}/items")
    public List<OrderItemDTO> getProductsByOrder(@PathParam("id") Long orderId) throws MyEntityNotFoundException {
        Orderr order = orderBean.findOrFail(orderId);
        List<OrderItem> orderItems = order.getOrderItems();
        return orderItems.stream().map(OrderItemDTO::from).collect(Collectors.toList());
    }
*/
    @POST
    @Path("/")
    public Response createNewOrder(OrderDTO orderDTO)
            throws MyEntityNotFoundException, MyConstraintViolationException {
        long id = orderBean.create(
                orderDTO.getStatus(),
                orderDTO.getEndConsumerName(),
<<<<<<< Updated upstream
                orderDTO.getLogisticsOperatorName(),
                orderDTO.getProductIds()
=======
                orderDTO.getLogisticsOperatorName()
>>>>>>> Stashed changes
        );

        Orderr order = orderBean.findOrFail(id);
        if (order == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        return Response.status(Response.Status.CREATED).entity(toDTO(order)).build();
    }

    @PUT
    @Path("{id}")
    public Response updateOrder(@PathParam("id") Long id, OrderDTO orderDTO)
    throws MyEntityNotFoundException {
        orderBean.update(
                id,
                orderDTO.getStatus(),
                orderDTO.getEndConsumerName(),
                orderDTO.getLogisticsOperatorName()
        );
        System.out.println("update order id: " + id);
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
        String logisticsOperatorName = order.getLogisticsOperators() != null ? order.getLogisticsOperators().getName() : null;
        return new OrderDTO(
                order.getId(),
                order.getStatus(),
                order.getEndConsumer().getName(),
                logisticsOperatorName
        );
    }
/*
    private OrderDTO toDTO2(Orderr order) {
        return new OrderDTO(
                order.getId(),
                order.getStatus(),
                order.getEndConsumer().getName(),
                "",
                order.getProductIds()
        );

    }

    /*
    private OrderDTO toDo(Orderr order) {
        return new OrderDTO(
                order.getId(),
                order.getStatus(),
                order.getEndConsumer().getName(),
                order.getLogisticsOperators().getName()
        );
    }
<<<<<<< Updated upstream
    */

=======
*/
>>>>>>> Stashed changes
}
