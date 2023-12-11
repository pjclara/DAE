package pt.ipleiria.estg.dei.ei.dae.backend.ejbs;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.LockModeType;
import jakarta.persistence.PersistenceContext;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.*;
import pt.ipleiria.estg.dei.ei.dae.backend.exceptions.MyEntityNotFoundException;

import java.util.List;

@Stateless
public class OrderBean {

    @PersistenceContext
    private EntityManager entityManager;

    @EJB
    private OrderBean orderBean;

    public List<Orderr> all() {
        return entityManager.createNamedQuery("getAllOrders", Orderr.class).getResultList();
    }

    public void create(Long id, String status, String endConsumerUsername, String logisticOptUsername) {
        // check logisticOpt exists
        LogisticsOperator logisticOpt = entityManager.find(LogisticsOperator.class, logisticOptUsername);
        if (logisticOpt == null) throw new IllegalArgumentException("Logistics Operator with username " + logisticOptUsername + " not found");
        // check endCostumer
        EndConsumer endConsumer = entityManager.find(EndConsumer.class, endConsumerUsername);
        if (endConsumer == null) throw new IllegalArgumentException("End Consumer with username " + endConsumerUsername + " not found");

        // check if order already exists
        //Orderr order = entityManager.find(Orderr.class, id);
        //if (order!= null){ throw new EntityNotFoundException("Orderr with id '" + id + "' already exists"); }

        Orderr order = new Orderr(id, status, endConsumer, logisticOpt);
        entityManager.persist(order);
        System.out.println("add order to endConsumer");
        endConsumer.addOrder(order);
        System.out.println("add order to logisticOpt");
        logisticOpt.addOrder(order);
    }

    public void update(Long id, String status, String endConsumerName, String logisticOptName)
            throws MyEntityNotFoundException {

        Orderr order = findOrFail(id);

        if (order == null){
            throw new EntityNotFoundException("Orderr with id '" + id + "' not found in database");
        }
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

    public void delete(Long id) throws MyEntityNotFoundException {
        var order = findOrFail(id);
        if (order != null) {
            entityManager.remove(order);
        }
    }

    public Orderr find(Long id) throws MyEntityNotFoundException {
        return null;
    }

    public Orderr findOrFail(Long id) throws MyEntityNotFoundException {

        //Hibernate.initialize(order);
        return null;
    }

}
