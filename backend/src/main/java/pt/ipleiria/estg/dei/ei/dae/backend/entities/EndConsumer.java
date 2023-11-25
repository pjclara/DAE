package pt.ipleiria.estg.dei.ei.dae.backend.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.NamedQuery;

@Entity
@NamedQuery(
        name = "getAllEndConsumers",
        query = "SELECT ec FROM EndConsumer ec ORDER BY ec.name ") // JPQL
public class EndConsumer extends User{
    public EndConsumer() {
    }

    public EndConsumer(String username, String password, String name, String email, String role) {
        super(username, password, name, email, role);
    }


}
