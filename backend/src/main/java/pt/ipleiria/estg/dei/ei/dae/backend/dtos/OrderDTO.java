package pt.ipleiria.estg.dei.ei.dae.backend.dtos;

import pt.ipleiria.estg.dei.ei.dae.backend.entities.Orderr;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class OrderDTO {

    private Long id;
    private String status;
    private String endConsumerName;
    private String logisticsOperatorName;
    private List<OrderItemDTO> OrderItems;
    private long packageId;


    public OrderDTO(){
        this.OrderItems = new ArrayList<>();
    }
    public OrderDTO(Long id, String status, String endConsumerName, String logisticsOperatorName, long packageId,
                    List<OrderItemDTO> orderItems) {
        this.id = id;
        this.status = status;
        this.endConsumerName = endConsumerName;
        this.logisticsOperatorName = logisticsOperatorName;
        this.packageId = packageId;
        this.OrderItems = orderItems;
    }

    public OrderDTO(Long id, String status, String username, String username1, Long id1) {
        this.id = id;
        this.status = status;
        this.endConsumerName = username;
        this.logisticsOperatorName = username1;
        this.packageId = id1;
    }

    public OrderDTO(Long id, String status, List<OrderItemDTO> orderItemDTOS) {
        this.id = id;
        this.status = status;
        this.OrderItems = orderItemDTOS;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEndConsumerName() {
        return endConsumerName;
    }

    public void setEndConsumerName(String name) {
        this.endConsumerName = name;
    }

    public String getLogisticsOperatorName() {
        return logisticsOperatorName;
    }

    public void setLogisticsOperatorName(String logisticsOperatorName) {
        this.logisticsOperatorName = logisticsOperatorName;
    }

    public long getPackageId() {
        return packageId;
    }

    public void setPackageId(long packageId) {
        this.packageId = packageId;
    }

    public List<OrderItemDTO> getOrderItems() {
        return OrderItems;
    }

    public void setOrderItems(List<OrderItemDTO> orderItems) {
        OrderItems = orderItems;
    }

    public static List<OrderDTO> toDTOs(List<Orderr> orders) {
        return orders.stream().map(OrderDTO::toDTO).collect(Collectors.toList());
    }

    public static OrderDTO toDTO(Orderr orderr) {
        return new OrderDTO(
                orderr.getId(),
                orderr.getStatus(),
                orderr.getEndConsumer().getName(),
                orderr.getLogisticsOperators() != null ? orderr.getLogisticsOperators().getName() : null,
                orderr.getOrderPackage() != null ? orderr.getOrderPackage().getId() : 0L,
                OrderItemDTO.toDTOs(orderr.getOrderItems())
        );
    }
}
