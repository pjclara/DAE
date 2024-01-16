package pt.ipleiria.estg.dei.ei.dae.backend.ejbs;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.hibernate.Hibernate;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.Manufacturer;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.Product;
import pt.ipleiria.estg.dei.ei.dae.backend.exceptions.MyEntityNotFoundException;
import pt.ipleiria.estg.dei.ei.dae.backend.security.Hasher;

import java.util.List;

@Stateless
public class ManufacturerBean {

    @PersistenceContext
    private EntityManager entityManager;

    private Hasher hasher = new Hasher();

    public void create(String username, String password, String name, String email, String role) {
        var manufacturer = new Manufacturer(username, hasher.hash(password), name, email, role);
        entityManager.persist(manufacturer);
    }

    public List<Manufacturer> all() {
        return entityManager.createNamedQuery("getAllManufacturers", Manufacturer.class).getResultList();
    }

    public Manufacturer find(String username) throws MyEntityNotFoundException {
        return entityManager.find(Manufacturer.class, username);
    }

    public void delete(String username) throws MyEntityNotFoundException {
        var manufacturer = find(username);
        if (manufacturer != null) {
            entityManager.remove(manufacturer);
        }
    }

    public void update(String username, String password, String name, String email, String role) throws MyEntityNotFoundException {
        var manufacturer = find(username);
        if (manufacturer != null) {
            manufacturer.setPassword(password);
            manufacturer.setName(name);
            manufacturer.setEmail(email);
            manufacturer.setRole(role);
            entityManager.merge(manufacturer);
        }
    }
    public Manufacturer getAllProductsFromManufacturer(String username) {
        Manufacturer manufacturer = entityManager.find(Manufacturer.class, username);

        if(manufacturer == null) throw new IllegalArgumentException("Manufacturer with username " + username + " not found in database");

        if (manufacturer.getProducts().isEmpty()) throw new IllegalArgumentException("Manufacturer with username " + username + " has no products");

        if (!manufacturer.getProducts().isEmpty()){
            manufacturer.getProducts().forEach(product -> {
                Hibernate.initialize(product.getUnitProducts());
                product.getUnitProducts().forEach(unitProduct -> {
                    if (unitProduct.getPackageSensor() != null) {
                        Hibernate.initialize(unitProduct.getPackageSensor());
                        Hibernate.initialize(unitProduct.getPackageSensor().getPackagging());
                    }
                });
            });
        }



        return manufacturer;
    }

    public Product getProductDetails(String username, Long id) {
        Manufacturer manufacturer = entityManager.find(Manufacturer.class, username);

        if(manufacturer == null) throw new IllegalArgumentException("Manufacturer with username " + username + " not found in database");

        Product product = entityManager.find(Product.class, id);

        if(product == null) throw new IllegalArgumentException("Product with id " + id + " not found in database");

        // check if product belongs to manufacturer
        if(!manufacturer.getProducts().contains(product)) throw new IllegalArgumentException("Product with id " + id + " does not belong to manufacturer with username " + username);
        return product;
    }
}
