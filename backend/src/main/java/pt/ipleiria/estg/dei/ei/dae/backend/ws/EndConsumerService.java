package pt.ipleiria.estg.dei.ei.dae.backend.ws;

import jakarta.ejb.EJB;
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

        return new OrderDTO(
                order.getId(),
                order.getStatus(),
                order.getEndConsumer().getName(),
                logisticsOperatorName,
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
                unitProduct.getAvailable(),
                productToDTO(unitProduct.getProduct()),
                packageSensorToDTO(unitProduct.getPackageSensor()));
    }

    private PackageSensorDTO packageSensorToDTO(PackageSensor packageSensor) {
        return new PackageSensorDTO(
                packageSensor.getId(),
                sensorValueDTOs(packageSensor.getSensorValues()),
                packageDTO(packageSensor.getPackagging() == null ? null : packageSensor.getPackagging()));
    }

    private List<SensorValueDTO> sensorValueDTOs(List<SensorValue> sensorValues) {
        return sensorValues.stream().map(this::sensorValueDTO).collect(java.util.stream.Collectors.toList());
    }

    private SensorValueDTO sensorValueDTO(SensorValue sensorValue) {
        return new SensorValueDTO(
                sensorValue.getId(),
                SensorDTO.toDTO(sensorValue.getSensor()),
                sensorValue.getValue()
        );
    }

    private ProductDTO productToDTO(Product product) {
        return new ProductDTO(
                product.getId(),
                product.getName(),
                product.getStock(),
                product.getImage(),
                product.getManufacturer().getUsername()
        );
    }

    private PackageDTO packageDTO(Package aPackage) {
        return new PackageDTO(
                aPackage.getId(),
                aPackage.getPackagingType(),
                aPackage.getPackagingMaterial()
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
