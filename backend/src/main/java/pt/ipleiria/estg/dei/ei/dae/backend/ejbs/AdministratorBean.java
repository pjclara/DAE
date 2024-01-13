package pt.ipleiria.estg.dei.ei.dae.backend.ejbs;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import pt.ipleiria.estg.dei.ei.dae.backend.entities.Administrator;
import pt.ipleiria.estg.dei.ei.dae.backend.exceptions.MyConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.backend.security.Hasher;

@Stateless
public class AdministratorBean {
    @PersistenceContext
    private EntityManager entityManager;

    private final Hasher hasher = new Hasher();

    public void create(String username, String password, String name, String email, String role) throws MyConstraintViolationException {
        Administrator administratorFind = entityManager.find(Administrator.class, username);
        if (administratorFind != null) {
            throw new IllegalArgumentException("Administrator with username " + username + " already exists");
        }
        Administrator administrator = new Administrator(username, hasher.hash(password), name, email, role);
        entityManager.persist(administrator);
    }
}