package pt.ipleiria.estg.dei.ei.dae.backend.dtos;

import pt.ipleiria.estg.dei.ei.dae.backend.entities.PackageOrder;

import java.util.ArrayList;
import java.util.List;

public class OrderDTO {

    private Long id;
    private String status;
    private String endConsumerName;
    private String logisticsOperatorName;
    private List<OrderItemDTO> OrderItems;
    private PackageOrderDTO packageOrder;

    private PackageSensorDTO packageSensorDTO;


    public OrderDTO(){
        this.OrderItems = new ArrayList<>();
    }
    public OrderDTO(Long id, String status, String endConsumerName, String logisticsOperatorName,
                    List<OrderItemDTO> orderItems) {
        this.id = id;
        this.status = status;
        this.endConsumerName = endConsumerName;
        this.logisticsOperatorName = logisticsOperatorName;
        this.OrderItems = orderItems;
    }

    public OrderDTO(Long id, String status, String endConsumerName, String logisticsOperatorName,
                    List<OrderItemDTO> orderItems, PackageOrderDTO packageOrder) {
        this.id = id;
        this.status = status;
        this.endConsumerName = endConsumerName;
        this.logisticsOperatorName = logisticsOperatorName;
        this.OrderItems = orderItems;
        this.packageOrder = packageOrder;
    }

    public OrderDTO(Long id, String status, String name, String logisticsOperatorName, List<OrderItemDTO> orderItemDTOS,
                    PackageOrderDTO packageOrderDTO, PackageSensorDTO packageSensorDTO) {
        this.id = id;
        this.status = status;
        this.endConsumerName = name;
        this.logisticsOperatorName = logisticsOperatorName;
        this.OrderItems = orderItemDTOS;
        this.packageOrder = packageOrderDTO;
        this.packageSensorDTO = packageSensorDTO;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public void setLogisticsOperatorName(String logisticsOperatorName) {
        this.logisticsOperatorName = logisticsOperatorName;
    }
    public List<OrderItemDTO> getOrderItems() {
        return OrderItems;
    }

    public PackageOrderDTO getPackageOrder() {
        return packageOrder;
    }

    public String getStatus() {
        return status;
    }

    public String getEndConsumerName() {
        return endConsumerName;
    }

    public String getLogisticsOperatorName() {
        return logisticsOperatorName;
    }

    public PackageSensorDTO getPackageSensorDTO() {
        return packageSensorDTO;
    }


    public void setPackageSensorDTO(PackageSensorDTO packageSensorDTO) {
        this.packageSensorDTO = packageSensorDTO;
    }

    public void setEndConsumerName(String endConsumerName) {
        this.endConsumerName = endConsumerName;
    }

    public void setOrderItems(List<OrderItemDTO> orderItems) {
        OrderItems = orderItems;
    }
}
