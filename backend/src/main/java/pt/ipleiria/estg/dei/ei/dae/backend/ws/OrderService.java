package pt.ipleiria.estg.dei.ei.dae.backend.ws;

import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import pt.ipleiria.estg.dei.ei.dae.backend.dtos.*;
import pt.ipleiria.estg.dei.ei.dae.backend.dtos.ManufacturerDTO;
import pt.ipleiria.estg.dei.ei.dae.backend.ejbs.OrderBean;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.*;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.Package;
import pt.ipleiria.estg.dei.ei.dae.backend.exceptions.MyConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.backend.exceptions.MyEntityNotFoundException;

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
        List<Orderr> orders = orderBean.getAll();
        return OrderDTO.toDTOs(orders);
    }

    @GET
    @Path("{id}")
    public Response get(@PathParam("id") Long orderId) throws MyEntityNotFoundException {
        Orderr orderr = orderBean.getOrderProducts(orderId);
        if (orderr != null) {
            var order = OrderDTO.toDTO(orderr);
            return Response.ok(order).build();
        }
        return Response.status(Response.Status.NOT_FOUND)
                .entity("ERROR_FINDING_ORDER")
                .build();
    }

    @GET
    @Path("{id}/items")
    public Response getProductsByOrder(@PathParam("id") Long orderId) throws MyEntityNotFoundException {
        Orderr orderr = orderBean.getOrderProducts(orderId);
        if(orderr != null) {
            var orderItems = OrderItemDTO.toDTOs(orderr.getOrderItems());
            return Response.ok(orderItems).build();
        }
    return Response.status(Response.Status.NOT_FOUND)
                .entity("ERROR_FINDING_ORDER")
                .build();
    }
    public static ProductDTO productDTO(Product product) {
        return new ProductDTO(
                product.getId(),
                product.getName(),
                product.getStock(),
                product.getImage(),
                product.getManufacturer().getUsername()
        );
    }


    @POST
    @Path("/")
    public Response createNewOrder(String username, String status, String orders)
            throws MyEntityNotFoundException, MyConstraintViolationException {
        long id = orderBean.create(
                username,
                orders);

        Orderr order = orderBean.findOrFail(id);
        if (order == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        return Response.status(Response.Status.CREATED).entity(OrderDTO.toDTO(order)).build();
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
        return Response.status(Response.Status.OK).entity(OrderDTO.toDTO(order)).build();
    }

    @DELETE
    @Path("{id}")
    public Response deleteEndConsumer(@PathParam("id") Long orderId) throws MyEntityNotFoundException {
        orderBean.delete(orderId);
        return Response.ok().build();
    }


}
