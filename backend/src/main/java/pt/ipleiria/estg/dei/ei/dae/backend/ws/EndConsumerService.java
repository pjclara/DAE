package pt.ipleiria.estg.dei.ei.dae.backend.ws;

import jakarta.ejb.EJB;
import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import jakarta.json.bind.Jsonb;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pt.ipleiria.estg.dei.ei.dae.backend.dtos.*;
import pt.ipleiria.estg.dei.ei.dae.backend.ejbs.EndConsumerBean;
import pt.ipleiria.estg.dei.ei.dae.backend.ejbs.OrderBean;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.*;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.Package;
import pt.ipleiria.estg.dei.ei.dae.backend.exceptions.MyConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.backend.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.dei.ei.dae.backend.exceptions.MyEntityNotFoundException;

import java.io.StringReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@Path("/endConsumers")
@Produces({MediaType.APPLICATION_JSON}) // injects header “Content-Type: application/json”
@Consumes({MediaType.APPLICATION_JSON}) // injects header “Accept: application/json”
public class EndConsumerService {

    @EJB
    private EndConsumerBean endConsumerBean;

    @EJB
    private OrderBean orderBean;

    // get all end consumers
    @GET
    @Path("/")
    public List<EndConsumerDTO> getAllEndConsumers() {
        return toDTOs(endConsumerBean.all());
    }

    // create new end consumer
    @POST
    @Path("/")
    public Response createNewEndConsumer(EndConsumerDTO endConsumerDTO)
            throws MyEntityExistsException, MyEntityNotFoundException, MyConstraintViolationException {
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
    // get end consumer by username
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
    // update end consumer
    @PUT
    @Path("{username}")
    public Response updateEndConsumer(@PathParam("username") String username, EndConsumerDTO endConsumerDTO)
    throws MyEntityExistsException, MyEntityNotFoundException {
        endConsumerBean.update(
                endConsumerDTO.getUsername(),
                endConsumerDTO.getPassword(),
                endConsumerDTO.getName(),
                endConsumerDTO.getEmail(),
                endConsumerDTO.getRole()
        );
        EndConsumer endConsumer = endConsumerBean.find(username);
        if (endConsumer == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.status(Response.Status.OK).entity(toDTO(endConsumer)).build();
    }
    // consumer create a new order
    @POST
    @Path("{username}/orders")
    public Response createNewOrder(@PathParam("username") String username, String data)
            throws MyEntityNotFoundException, MyConstraintViolationException {

        long orderId = orderBean.create(
                username,
                data
        );
        Orderr order = orderBean.findOrFail(orderId);
        if (order == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        return Response.status(Response.Status.CREATED).entity(orderToDTOs(order)).build();
    }
    // delete end consumer
    @DELETE
    @Path("{username}")
    public Response deleteEndConsumer(@PathParam("username") String username) {
        endConsumerBean.delete(username);
        return Response.ok().build();
    }

    // get all orders from end consumer
    @GET
    @Path("{username}/orders")
    public Response getOrdersFromEndConsumer(@PathParam("username") String username)
            throws MyEntityExistsException, MyEntityNotFoundException{
        EndConsumer endConsumer = endConsumerBean.getOrdersFromEndConsumer(username);

        if (endConsumer != null) {
            var dtos = ordersToDTOs(endConsumer.getOrders());
            return Response.ok(dtos).build();
        }
        return Response.status(Response.Status.NOT_FOUND)
                .entity("ERROR_FINDING_ENDCONSUMER")
                .build();
    }
    // Auxiliary functions
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
    private List<OrderDTO> ordersToDTOs(List<Orderr> orders) {
        return orders.stream().map(this::orderToDTOs).collect(java.util.stream.Collectors.toList());
    }
    private OrderDTO orderToDTOs(Orderr order) {
        String logisticsOperatorName = order.getLogisticsOperators() != null ? order.getLogisticsOperators().getName() : null;
        Long packageId = order.getOrderPackage() != null ? order.getOrderPackage().getId() : 0L;

        return new OrderDTO(
                order.getId(),
                order.getStatus(),
                order.getEndConsumer().getName(),
                logisticsOperatorName,
                packageId,
                orderItemsToDTOs(order.getOrderItems())
        );
    }

    private List<OrderItemDTO> orderItemsToDTOs(List<OrderItem> orderItems) {
        return orderItems.stream().map(this::orderItemToDTOs).collect(java.util.stream.Collectors.toList());
    }

    private OrderItemDTO orderItemToDTOs(OrderItem orderItem) {
        return new OrderItemDTO(
                orderItem.getId(),
                orderItem.getQuantity(),
                unitProductDTO(orderItem.getUnitProduct())
        );
    }

    private UnitProductDTO unitProductDTO(UnitProduct unitProduct) {
        return new UnitProductDTO(
                unitProduct.getId(),
                unitProduct.getSerialNumber(),
                productToDTO(unitProduct.getProduct())
        );
    }

    private ProductDTO productToDTO(Product product) {
        return new ProductDTO(
                product.getId(),
                product.getName(),
                product.getStock(),
                product.getImage(),
                product.getManufacturer().getUsername(),
                packageDTO(product.getProductPackage() == null ? new Package() : product.getProductPackage())
        );
    }

    private PackageDTO packageDTO(Package aPackage) {
        return new PackageDTO(
                aPackage.getId(),
                aPackage.getPackagingType(),
                aPackage.getPackagingMaterial(),
                sensorsDTO(aPackage.getSensors())
        );
    }


    private List<SensorDTO> sensorsDTO(List<Sensor> sensors) {
        return sensors.stream().map(this::sensorDTO).collect(java.util.stream.Collectors.toList());
    }

    private SensorDTO sensorDTO(Sensor sensor) {
        return new SensorDTO(
                sensor.getId(),
                sensor.getSource(),
                sensor.getType(),
                sensor.getValue(),
                sensor.getUnit(),
                sensor.getMax(),
                sensor.getMin(),
                sensor.getTimestamp(),
                sensor.getPackagging().getId()

        );
    }

    private List<SensorDTO> sensorToDTOs(List<Sensor> sensors) {
        return sensors.stream().map(this::sensorToDTO).collect(java.util.stream.Collectors.toList());
    }

    private SensorDTO sensorToDTO(Sensor sensor) {
        return new SensorDTO(
                sensor.getId(),
                sensor.getSource(),
                sensor.getType(),
                sensor.getValue(),
                sensor.getUnit(),
                sensor.getMax(),
                sensor.getMin(),
                sensor.getTimestamp(),
                sensor.getPackagging().getId()

        );
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

}
