package pt.ipleiria.estg.dei.ei.dae.backend.ejbs;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.EndConsumer;
import pt.ipleiria.estg.dei.ei.dae.backend.security.Hasher;

import java.util.List;

@Stateless
public class EndConsumerBean {
    @PersistenceContext
    private EntityManager entityManager;

    private Hasher hasher = new Hasher();

    public void create(String username, String password, String name, String email, String role) {
        var endConsumer = new EndConsumer(username, hasher.hash(password), name, email, role);
        entityManager.persist(endConsumer);
    }

    public List<EndConsumer> all() {
        return entityManager.createNamedQuery("getAllEndConsumers", EndConsumer.class).getResultList();
    }

    public EndConsumer find(String username) {
        return entityManager.find(EndConsumer.class, username);
    }

    public void delete(String username) {
        var endConsumer = find(username);
        if (endConsumer != null) {
            entityManager.remove(endConsumer);
        }
    }

    public void update(String username, String password, String name, String email, String role) {
        var endConsumer = find(username);
        if (endConsumer != null) {
            endConsumer.setPassword(password);
            endConsumer.setName(name);
            endConsumer.setEmail(email);
            endConsumer.setRole(role);
            entityManager.merge(endConsumer);
        }
    }
}
