package pt.ipleiria.estg.dei.ei.dae.backend.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.NamedQuery;

import java.util.ArrayList;
import java.util.List;

@Entity
@NamedQuery(
        name = "getAllAdministrators",
        query = "SELECT a FROM Administrator a ORDER BY a.name ") // JPQL
public class Administrator extends User{

    public Administrator() {
    }
    public Administrator(String username, String password, String name, String email, String role) {
        super(username, password, name, email, role);
    }
}
