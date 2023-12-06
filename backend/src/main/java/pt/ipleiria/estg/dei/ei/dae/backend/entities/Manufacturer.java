package pt.ipleiria.estg.dei.ei.dae.backend.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.NamedQuery;

import java.util.List;

@Entity
@NamedQuery(
        name = "getAllManufacturers",
        query = "SELECT m FROM Manufacturer m ORDER BY m.name ") // JPQL
public class Manufacturer extends User{

    private List<Product> products;
    public Manufacturer() {
    }

    public Manufacturer(String username, String password, String name, String email, String role) {
        super(username, password, name, email, role);
    }

}
