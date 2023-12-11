package pt.ipleiria.estg.dei.ei.dae.backend.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;

import java.util.ArrayList;
import java.util.List;

@Entity
@NamedQuery(
        name = "getAllEndConsumers",
        query = "SELECT ec FROM EndConsumer ec ORDER BY ec.name ") // JPQL
public class EndConsumer extends User{
    @OneToMany(mappedBy = "endConsumer")
    private List<Orderr> orders;
    public EndConsumer() {
        this.orders = new ArrayList<>();
    }
    public EndConsumer(String username, String password, String name, String email, String role) {
        super(username, password, name, email, role);
        this.orders = new ArrayList<>();
    }

    public List<Orderr> getOrders() {
        return orders;
    }

    public void addOrder(Orderr order) {
        this.orders.add(order);
        order.setEndConsumer(this);
    }
}
