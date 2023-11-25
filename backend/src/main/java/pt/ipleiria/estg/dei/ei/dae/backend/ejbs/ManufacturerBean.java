package pt.ipleiria.estg.dei.ei.dae.backend.ejbs;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.Manufacturer;

import java.util.List;

@Stateless
public class ManufacturerBean {

    @PersistenceContext
    private EntityManager entityManager;

    public void create(String username, String password, String name, String email, String role) {
        var manufacturer = new Manufacturer(username, password, name, email, role);
        entityManager.persist(manufacturer);
    }

    public List<Manufacturer> all() {
        return entityManager.createNamedQuery("getAllManufacturers", Manufacturer.class).getResultList();
    }

    public Manufacturer find(String username) {
        return entityManager.find(Manufacturer.class, username);
    }

    public void delete(String username) {
        var manufacturer = find(username);
        if (manufacturer != null) {
            entityManager.remove(manufacturer);
        }
    }

    public void update(String username, String password, String name, String email, String role) {
        var manufacturer = find(username);
        if (manufacturer != null) {
            manufacturer.setPassword(password);
            manufacturer.setName(name);
            manufacturer.setEmail(email);
            manufacturer.setRole(role);
            entityManager.merge(manufacturer);
        }
    }


}
