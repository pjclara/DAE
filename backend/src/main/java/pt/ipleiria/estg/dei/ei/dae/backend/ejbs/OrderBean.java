package pt.ipleiria.estg.dei.ei.dae.backend.ejbs;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.LockModeType;
import jakarta.persistence.PersistenceContext;
import jakarta.validation.ConstraintViolationException;
import org.hibernate.Hibernate;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.EndConsumer;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.LogisticsOperator;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.Order;
import pt.ipleiria.estg.dei.ei.dae.backend.exceptions.MyConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.backend.exceptions.MyEntityNotFoundException;

import java.util.List;

@Stateless
public class OrderBean {

    @PersistenceContext
    private EntityManager entityManager;

    @EJB
    private OrderBean orderBean;

    public List<Order> all() {
        return entityManager.createNamedQuery("getAllOrders", Order.class).getResultList();
    }

    public void create(Long id, String status, String endConsumerName, String logisticOptName) throws MyConstraintViolationException, MyEntityNotFoundException {
        if (orderBean.find(id) != null){
            throw new EntityNotFoundException("Order with id '" + id + "' already exists");
        }
        // check LogisticOpt.
        LogisticsOperator logisticOpt = entityManager.find(LogisticsOperator.class, logisticOptName);
        // check endCostumer
        EndConsumer consumer = entityManager.find(EndConsumer.class, endConsumerName);

        if (logisticOpt == null) throw new IllegalArgumentException("Logistics Operator with username " + logisticOptName + " not found");
        if (consumer == null) throw new IllegalArgumentException("End Consumer with username " + consumer + " not found");

        try {
            Order order = new Order(id, status, consumer, logisticOpt);
            entityManager.persist(order);
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(e);
        }
    }

    public void update(Long id, String status, String endConsumerName, String logisticOptName)
            throws MyEntityNotFoundException {

        /*if (orderBean.find(id) == null){
            throw new EntityNotFoundException("Order with id '" + id + "' not found in database");
        }*/
        var orderId = findOrFail(id).getId();

        Order order = entityManager.find(Order.class, orderId);
        entityManager.lock(order, LockModeType.OPTIMISTIC);

        order.setStatus(status);

        if (endConsumerName != null && !endConsumerName.equals(order.getEndConsumer().getUsername())) {
            EndConsumer consumer = entityManager.find(EndConsumer.class, endConsumerName);
            if (consumer == null) throw new IllegalArgumentException("End Consumer with username " + consumer + " not found");
            order.setEndConsumer(consumer);
        }

        if (logisticOptName != null && !logisticOptName.equals(order.getLogisticsOperators().getUsername())) {
            LogisticsOperator logisticOpt = entityManager.find(LogisticsOperator.class, logisticOptName);
            if (logisticOpt == null) throw new IllegalArgumentException("Logistics Operator with username " + logisticOptName + " not found");
            order.setLogisticsOperators(logisticOpt);
        }

        entityManager.merge(order);
    }

    public Order find(Long id) throws MyEntityNotFoundException {
        return entityManager.find(Order.class, id);
    }

    public Order findOrFail(Long id) throws MyEntityNotFoundException {
        var order = find(id);
        if (order == null) {
            throw new jakarta.persistence.EntityNotFoundException("Order with id '" + id + "' not found");
        }
        Hibernate.initialize(order);
        return order;
    }


}
