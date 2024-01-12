package pt.ipleiria.estg.dei.ei.dae.backend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "order-items")
@NamedQuery(name = "getAllOrdersItems", query = "SELECT o FROM OrderItem o ORDER BY o.id")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer quantity;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "unitProduct_id")
    private UnitProduct unitProduct;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Orderr order;

    public OrderItem() {
    }

    public OrderItem(UnitProduct unitProduct, Integer quantity, Orderr order) {
        this.unitProduct = unitProduct;
        this.quantity = quantity;
        this.order = order;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public void setProduct(UnitProduct unitProduct) {
        this.unitProduct = unitProduct;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Orderr getOrder() {
        return order;
    }

    public void setOrder(Orderr order) {
        this.order = order;
    }

    public void setOrderr(Orderr order) {
        this.order = order;
    }

    public UnitProduct getUnitProduct() {
        return unitProduct;
    }
}
