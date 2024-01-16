package pt.ipleiria.estg.dei.ei.dae.backend.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;

import java.util.ArrayList;
import java.util.List;

@Entity
@NamedQuery(
        name = "getAllLogisticsOperators",
        query = "SELECT lo FROM LogisticsOperator lo ORDER BY lo.name ") // JPQL
public class LogisticsOperator extends User{

    public LogisticsOperator() {
    }
    public LogisticsOperator(String username, String password, String name, String email, String role) {
        super(username, password, name, email, role);
    }

}
