package pt.ipleiria.estg.dei.ei.dae.backend.dtos;

import pt.ipleiria.estg.dei.ei.dae.backend.entities.OrderItem;

import java.util.ArrayList;
import java.util.List;

public class OrderDTO {

    private Long id;
    private String status;
    private String endConsumerName;
    private String logisticsOperatorName;
    private List<ArrayList> OrderItems;
    private long packageId;


    public OrderDTO(){
        this.OrderItems = new ArrayList<>();
    }
    public OrderDTO(Long id, String status, String endConsumerName, String logisticsOperatorName, long packageId) {
        this.id = id;
        this.status = status;
        this.endConsumerName = endConsumerName;
        this.logisticsOperatorName = logisticsOperatorName;
        this.packageId = packageId;
        this.OrderItems = new ArrayList<>();
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

    public List<ArrayList> getOrderItems() {
        return OrderItems;
    }

    public void setOrderItems(List<ArrayList> orderItems) {
        OrderItems = orderItems;
    }
}
