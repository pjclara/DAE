package pt.ipleiria.estg.dei.ei.dae.backend.dtos;

public class OrderDTO {

    private Long id;
    private String status;
    private String endConsumerName;
    private String logisticsOperatorName;

    public OrderDTO(){}
    public OrderDTO(Long id, String status, String endConsumerName, String logisticsOperatorName) {
        this.id = id;
        this.status = status;
        this.endConsumerName = endConsumerName;
        this.logisticsOperatorName = logisticsOperatorName;
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

    public String getLogisticsOperator() {
        return logisticsOperatorName;
    }

    public void setLogisticsOperatorName(String logisticsOperatorName) {
        this.logisticsOperatorName = logisticsOperatorName;
    }
}
