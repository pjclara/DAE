package pt.ipleiria.estg.dei.ei.dae.backend.ws;

import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import pt.ipleiria.estg.dei.ei.dae.backend.dtos.*;
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
        return toDTOs(orders);
    }

    @GET
    @Path("{id}")
    public Response get(@PathParam("id") Long orderId) throws MyEntityNotFoundException {
        Orderr orderr = orderBean.getOrderProducts(orderId);
        if (orderr != null) {
            var order = toDTO(orderr);
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
            var orderItems = ordersItemDTO(orderr.getOrderItems());
            return Response.ok(orderItems).build();
        }
    return Response.status(Response.Status.NOT_FOUND)
                .entity("ERROR_FINDING_ORDER")
                .build();
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
                orderDTO.getPackageOrder().getId()
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


    // get sensors not in de order
    @GET
    @Path("{id}/sensorsNotInOrder")
    public Response getSensorsNotInOrder(@PathParam("id") Long orderId) throws MyEntityNotFoundException {
        List<Sensor> sensors = orderBean.getSensorsNotInOrder(orderId);
        if (sensors.size() >= 0) {
            return Response.ok(sensorDTOs(sensors)).build();
        }
        return Response.status(Response.Status.NOT_FOUND)
                .entity("ERROR_FINDING_ORDER")
                .build();
    }

    // get sensors not the order
    @GET
    @Path("{id}/sensorsInOrder")
    public Response getSensorsInOrder(@PathParam("id") Long orderId) throws MyEntityNotFoundException {
        List<Sensor> sensors = orderBean.getSensorsInOrder(orderId);
        if (sensors.size() >= 0) {
            return Response.ok(sensorDTOs(sensors)).build();
        }
        return Response.status(Response.Status.NOT_FOUND)
                .entity("ERROR_FINDING_ORDER")
                .build();
    }

    private List<SensorDTO> sensorDTOs(List<Sensor> sensors) {
        return sensors.stream().map(this::sensorDTO).collect(Collectors.toList());
    }

    private SensorDTO sensorDTO(Sensor sensor) {
        return new SensorDTO(
                sensor.getId(),
                sensor.getSource(),
                sensor.getType(),
                sensor.getUnit(),
                sensor.getMax(),
                sensor.getMin()
        );
    }

    // uodate status

    @PUT
    @Path("{id}/status/{status}")
    public Response updateStatus(@PathParam("id") Long id,@PathParam("status") String status)
            throws MyEntityNotFoundException {
        long orderId = orderBean.updateStatus(
                id,
                status
        );

        if (orderId < 1) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.status(Response.Status.OK).build();
    }

    // set sensors to package order
    @PUT
    @Path("{id}/addSensor/{sensorId}")
    public Response addSensorToPackageOrder(@PathParam("id") Long id,@PathParam("sensorId") Long sensorId)
            throws MyEntityNotFoundException {
        long orderId = orderBean.addSensorToPackageOrder(
                id,
                sensorId
        );

        if (orderId < 1) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.status(Response.Status.OK).build();
    }


    private List<OrderItemDTO> ordersItemDTO(List<OrderItem> orderItems) {
        return orderItems.stream().map(this::orderItemDTO).collect(Collectors.toList());
    }

    private OrderItemDTO orderItemDTO(OrderItem orderItem) {
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
                productDTO(unitProduct.getProduct() == null ? new Product() : unitProduct.getProduct()),
                packageSensorToDTO(unitProduct.getPackageSensor() == null ?  null : unitProduct.getPackageSensor())
        );
    }

    private ProductDTO productDTO(Product product) {
        return new ProductDTO(
                product.getId(),
                product.getName(),
                product.getStock(),
                product.getImage(),
                product.getManufacturer().getUsername()
        );
    }

    private PackageSensorDTO packageSensorToDTO(PackageSensor packageSensor) {
        if (packageSensor != null)
            return new PackageSensorDTO(
                    packageSensor.getId(),
                    sensorValueDTOs(packageSensor.getSensorValues() == null ? null : packageSensor.getSensorValues()),
                    packageDTO(packageSensor.getPackagging() == null ? null : packageSensor.getPackagging()));
        else
            return null;
    }

    private List<SensorValueDTO> sensorValueDTOs(List<SensorValue> sensorValues) {
        return sensorValues.stream().map(this::sensorValueDTO).collect(Collectors.toList());
    }

    private SensorValueDTO sensorValueDTO(SensorValue sensorValue) {
        return new SensorValueDTO(
                sensorValue.getId(),
                SensorDTO.toDTO(sensorValue.getSensor()),
                sensorValue.getValue()
        );
    }

    private PackageDTO packageDTO(Package aPackage) {
        return new PackageDTO(
                aPackage.getId(),
                aPackage.getPackagingType(),
                aPackage.getPackagingMaterial()
        );
    }

    private List<OrderDTO> toDTOs(List<Orderr> orders) {
        return orders.stream().map(this::toDTO).collect(Collectors.toList());
    }

    private OrderDTO toDTO(Orderr order) {
        String logisticsOperatorName = order.getLogisticsOperators() != null ? order.getLogisticsOperators().getName() : null;
        return new OrderDTO(
                order.getId(),
                order.getStatus(),
                order.getEndConsumer().getName(),
                logisticsOperatorName,
                ordersItemDTO(order.getOrderItems()),
                packageOrderDTO(order.getPackageOrder() == null ? null : order.getPackageOrder()),
                packageSensorToDTO(order.getPackageSensor() == null ? null : order.getPackageSensor())
        );
    }

    private PackageOrderDTO packageOrderDTO(PackageOrder packageOrder) {
        if (packageOrder == null) return null;
        return new PackageOrderDTO(
                packageOrder.getId(),
                packageOrder.getPackagingType(),
                packageOrder.getPackagingMaterial()
        );
    }


}
