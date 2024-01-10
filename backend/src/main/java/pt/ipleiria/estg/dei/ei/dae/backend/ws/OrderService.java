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
import pt.ipleiria.estg.dei.ei.dae.backend.entities.Product;
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
    public List<OrderDTO> getOrdersWithOrderItems() {
        return toDTOs(orderBean.all());
    }

    @GET
    @Path("{id}")
    public Response get(@PathParam("id") Long orderId) throws MyEntityNotFoundException {
        Orderr orderr = orderBean.findOrFail(orderId);
        if (orderr != null) {
            var order = orderDto(orderr);
            return Response.ok(order).build();
        }
        return Response.status(Response.Status.NOT_FOUND)
                .entity("ERROR_FINDING_ORDER")
                .build();
    }

    private OrderDTO orderDto(Orderr orderr) {
        return new OrderDTO(
                orderr.getId(),
                orderr.getStatus(),
                orderr.getEndConsumer().getName(),
                orderr.getLogisticsOperators() != null ? orderr.getLogisticsOperators().getName() : null,
                orderr.getOrderPackage() != null ? orderr.getOrderPackage().getId() : 0L,
                ordersItemDTO(orderr.getOrderItems())
        );
    }

    @GET
    @Path("{id}/items")
    public Response getProductsByOrder(@PathParam("id") Long orderId) throws MyEntityNotFoundException {
        Orderr orderr = orderBean.findOrFail(orderId);
        if(orderr != null) {
            var orderItems = ordersItemDTO(orderr.getOrderItems());
            return Response.ok(orderItems).build();
        }
    return Response.status(Response.Status.NOT_FOUND)
                .entity("ERROR_FINDING_ORDER")
                .build();
    }

    private List<OrderItemDTO> ordersItemDTO(List<OrderItem> orderItems) {
        return orderItems.stream().map(this::orderItemDTO).collect(Collectors.toList());
    }

    private OrderItemDTO orderItemDTO(OrderItem orderItem) {
        return new OrderItemDTO(
                orderItem.getId(),
                orderItem.getProduct().getName(),
                orderItem.getProduct().getImage(),
                orderItem.getProduct().getProductPackage().getPackagingMaterial(),
                orderItem.getQuantity()
        );
    }

    @POST
    @Path("/")
    public Response createNewOrder(OrderDTO orderDTO)
            throws MyEntityNotFoundException, MyConstraintViolationException {
        System.out.println("OrderDTO: " + orderDTO);
        long id = orderBean.create(
                orderDTO.getStatus(),
                orderDTO.getEndConsumerName(),
                orderDTO.getOrderItems()
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
                orderDTO.getLogisticsOperatorName(),
                orderDTO.getPackageId()
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
        String logisticsOperatorName = order.getLogisticsOperators() != null ? order.getLogisticsOperators().getName() : null;
        Long packageId = order.getOrderPackage() != null ? order.getOrderPackage().getId() : 0L;
        return new OrderDTO(
                order.getId(),
                order.getStatus(),
                order.getEndConsumer().getName(),
                logisticsOperatorName,
                packageId,
                ordersItemDTO(order.getOrderItems())
        );
    }


}
