package pt.ipleiria.estg.dei.ei.dae.backend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "orders")
@NamedQuery(name = "getAllOrders", query = "SELECT o FROM Orderr o ORDER BY o.id")
public class Orderr {
    @Id
    private Long id;
    @NotNull
    private String status;
    @NotNull
    @ManyToOne
    private EndConsumer endConsumer;
    @ManyToOne
    private LogisticsOperator logisticsOperators;

    public Orderr() {
    }

    public Orderr(Long id, String status, EndConsumer endConsumer, LogisticsOperator logisticsOperators) {
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
