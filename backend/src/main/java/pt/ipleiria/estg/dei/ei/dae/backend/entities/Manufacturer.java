package pt.ipleiria.estg.dei.ei.dae.backend.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;

import java.util.ArrayList;
import java.util.List;

@Entity
@NamedQuery(
        name = "getAllManufacturers",
        query = "SELECT m FROM Manufacturer m ORDER BY m.name ") // JPQL
public class Manufacturer extends User{

    @OneToMany(mappedBy = "manufacturer")
    private List<Product> products;

    public Manufacturer() {
    }

    public Manufacturer(String username, String password, String name, String email, String role) {
        super(username, password, name, email, role);
        this.products = new ArrayList<>();
    }

    public List<Product> getProducts() {
        return products;
    }

    public void addProduct(Product product) {
        this.products.add(product);
        product.setManufacturer(this);
    }

}
