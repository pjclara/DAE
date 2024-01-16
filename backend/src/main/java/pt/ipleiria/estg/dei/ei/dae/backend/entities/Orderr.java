package pt.ipleiria.estg.dei.ei.dae.backend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
//@NamedQuery(name = "getAllOrders", query = "SELECT o FROM Orderr o ORDER BY o.id")
@NamedQueries({
        @NamedQuery(
                name = "getAllOrders",
                query = "SELECT o FROM Orderr o ORDER BY o.id"
        ),

        @NamedQuery(
                name = "getOrdersByEndConsumer",
                query = "SELECT o FROM Orderr o WHERE o.endConsumer.username = :endConsumerUsername ORDER BY o.id"
        ),
        // get orders with orderItems
        @NamedQuery(
                name = "getOrdersWithOrderItems",
                query = "SELECT o FROM Orderr o JOIN FETCH OrderItem orderItem WHERE o.id = orderItem.order.id ORDER BY o.id"
        )
})
public class Orderr extends Versionable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "TBL_METADATA_ID_SEQ")
    @Column(name="id")
    private Long id;
    @NotNull
    private String status;
    @NotNull
    @ManyToOne
    private EndConsumer endConsumer;
    @ManyToOne
    private LogisticsOperator logisticsOperators;

    @OneToOne
    @JoinColumn(name = "package_id")
    private PackageOrder packageOrder;

    @OneToMany(mappedBy = "order", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    private List<OrderItem> orderItems;

    public Orderr() {
        this.orderItems = new ArrayList<>();
    }

    public Orderr(String status, EndConsumer endConsumer) {
        this.status = status;
        this.endConsumer = endConsumer;
        this.orderItems = new ArrayList<>();
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

    public PackageOrder getOrderPackage() {
        return packageOrder;
    }

    public void setPackageOrder(PackageOrder packageOrder) {
        this.packageOrder = packageOrder;
    }


    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

}
