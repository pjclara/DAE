package pt.ipleiria.estg.dei.ei.dae.backend.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.NamedQuery;

import java.util.List;

@Entity
@NamedQuery(
        name = "getAllEndConsumers",
        query = "SELECT ec FROM EndConsumer ec ORDER BY ec.name ") // JPQL
public class EndConsumer extends User{

    private List<Order> orders;

    public EndConsumer() {


    }

    public EndConsumer(String username, String password, String name, String email, String role) {
        super(username, password, name, email, role);
    }


}
