package pt.ipleiria.estg.dei.ei.dae.backend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Order extends Versionable{
    @Id
    private Long id;
    @NotNull
    private boolean isDelivered;
    @ManyToOne
    @JoinColumn(name = "endConsumer_username")
    private EndConsumer endConsumer;
    @ManyToOne()
    private LogisticsOperator logisticsOperators;
    public Order() {
    }

    public Order(Long id, boolean isDelivered, EndConsumer endConsumer, LogisticsOperator logisticsOperators) {
        this.id = id;
        this.isDelivered = isDelivered;
        this.endConsumer = endConsumer;
        this.logisticsOperators = logisticsOperators;
    }

    public Order(boolean isDelivered) {
        this.isDelivered = isDelivered;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public boolean isDelivered() {
        return isDelivered;
    }

    public void setDelivered(boolean delivered) {
        isDelivered = delivered;
    }


    public EndConsumer getEndConsumer() {
        return endConsumer;
    }

    public void setEndConsumer(EndConsumer endConsumer) {
        this.endConsumer = endConsumer;
    }

    public LogisticsOperator getLogisticsOperators() {
        return logisticsOperators;
    }

    public void setLogisticsOperators(LogisticsOperator logisticsOperators) {
        this.logisticsOperators = logisticsOperators;
    }
}
