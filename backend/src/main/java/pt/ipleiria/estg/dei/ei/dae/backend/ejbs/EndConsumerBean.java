package pt.ipleiria.estg.dei.ei.dae.backend.ejbs;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.persistence.PersistenceContext;
import org.hibernate.Hibernate;
import pt.ipleiria.estg.dei.ei.dae.backend.dtos.OrderItemDTO;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.EndConsumer;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.OrderItem;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.Orderr;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.Product;
import pt.ipleiria.estg.dei.ei.dae.backend.exceptions.MyConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.backend.exceptions.MyEntityNotFoundException;
import pt.ipleiria.estg.dei.ei.dae.backend.security.Hasher;

import java.util.List;

@Stateless
public class EndConsumerBean {
    @PersistenceContext
    private EntityManager entityManager;

    private final Hasher hasher = new Hasher();

    public void create(String username, String password, String name, String email, String role) throws MyConstraintViolationException {
        EndConsumer endConsumerFind = entityManager.find(EndConsumer.class, username);
        if (endConsumerFind != null) {
            throw new IllegalArgumentException("End consumer with username " + username + " already exists");
        }
        EndConsumer endConsumer = new EndConsumer(username, hasher.hash(password), name, email, role);
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

    public void update(String username, String password, String name, String email, String role) throws MyEntityNotFoundException {

        if (!exists(username)) {throw new MyEntityNotFoundException("End consumer with username " + username + " not found in database"); }

        EndConsumer endConsumer = entityManager.find(EndConsumer.class, username);
        entityManager.lock(endConsumer, LockModeType.OPTIMISTIC);

        endConsumer.setPassword(hasher.hash(password));
        endConsumer.setName(name);
        endConsumer.setEmail(email);
        endConsumer.setRole(role);

        entityManager.merge(endConsumer);
    }

    public boolean exists(String username) {
        return entityManager.find(EndConsumer.class, username) != null;
    }

    public EndConsumer getOrdersFromEndConsumer(String username) throws MyEntityNotFoundException {
        EndConsumer endConsumer = entityManager.find(EndConsumer.class, username);

        if(endConsumer == null) throw new MyEntityNotFoundException("End consumer with username " + username + " not found in database");

        Hibernate.initialize(endConsumer.getOrders());

        endConsumer.getOrders().forEach(order -> {
            Hibernate.initialize(order.getOrderItems());
            order.getOrderItems().forEach(orderItem -> {
                Hibernate.initialize(orderItem.getProduct());
            });
        });

        if (endConsumer.getOrders().isEmpty()) throw new IllegalArgumentException("End consumer with username " + username + " has no orders");
        return endConsumer;
    }


}
