package pt.ipleiria.estg.dei.ei.dae.backend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;

@Entity
@NamedQuery(
        name = "getAllOrders",
        query = "SELECT o FROM Order o ORDER BY o.status")
public class Order extends Versionable{
    @Id
    private Long id;
    @NotNull
    private String status;
    @ManyToOne
    @JoinColumn(name = "endConsumer_username")
    private EndConsumer endConsumer;
    @ManyToOne()
    private LogisticsOperator logisticsOperators;
    public Order() {
    }

    public Order(Long id, String status, EndConsumer endConsumer, LogisticsOperator logisticsOperators) {
        this.id = id;
        this.status = status;
        this.endConsumer = endConsumer;
        this.logisticsOperators = logisticsOperators;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
