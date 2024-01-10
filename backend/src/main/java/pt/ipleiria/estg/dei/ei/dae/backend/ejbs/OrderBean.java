package pt.ipleiria.estg.dei.ei.dae.backend.ejbs;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.LockModeType;
import jakarta.persistence.PersistenceContext;
import org.hibernate.Hibernate;
import pt.ipleiria.estg.dei.ei.dae.backend.dtos.OrderItemDTO;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.*;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.Package;
import pt.ipleiria.estg.dei.ei.dae.backend.exceptions.MyConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.backend.exceptions.MyEntityNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Stateless
public class OrderBean {

    @PersistenceContext
    private EntityManager entityManager;

    @EJB
    private OrderBean orderBean;

    @EJB
    private ProductBean productBean;

    public List<Orderr> all() {
        return entityManager.createNamedQuery("getOrdersWithOrderItems", Orderr.class).getResultList();
    }

    public List<Orderr> getOrdersByEndConsumer(String customerName) {
        return entityManager.createNamedQuery("getOrdersByEndConsumer", Orderr.class)
                .setParameter("endConsumerUsername", customerName)
                .getResultList();
    }

    public long create(String status, String endConsumerUsername,  List<OrderItemDTO> orderItems) throws MyEntityNotFoundException, MyConstraintViolationException {

        // check endCostumer
        EndConsumer endConsumer = entityManager.find(EndConsumer.class, endConsumerUsername);
        if (endConsumer == null) throw new IllegalArgumentException("End Consumer with username " + endConsumerUsername + " not found");

        // create order
        Orderr order = new Orderr(status, endConsumer);
        entityManager.persist(order);

        // add order items
        for (OrderItemDTO item : orderItems) {
            // item.get(0) to a long
            Product product = productBean.find(item.getProductId());
            if (product == null) throw new IllegalArgumentException("Product with id " + item.getProductId() + " not found");

            OrderItem orderItem = new OrderItem(product, item.getQuantity(), order);
            orderItem.setOrderr(order);
            entityManager.persist(orderItem);

        }

        return order.getId();
    }

    public void update(Long id, String status, String endConsumerName, String logisticOptName, long packageId)
            throws MyEntityNotFoundException {

        //Orderr order = findOrFail(id);
        Orderr order = entityManager.find(Orderr.class, id);

        if (order == null){
            throw new EntityNotFoundException("Orderr with id '" + id + "' not found in database");
        }
        entityManager.lock(order, LockModeType.OPTIMISTIC);

        order.setStatus(status);

        if (logisticOptName != null) {
            LogisticsOperator logisticOpt = entityManager.find(LogisticsOperator.class, logisticOptName);
            if (logisticOpt == null) throw new IllegalArgumentException("Logistics Operator with username " + logisticOptName + " not found");
            order.setLogisticsOperators(logisticOpt);
        }
        if (packageId != 0){
            Package orderPackage = entityManager.find(Package.class, packageId);
            if (orderPackage == null) throw new IllegalArgumentException("Package with id " + packageId + " not found");
            order.setOrderPackage(orderPackage);
        }

        entityManager.merge(order);
    }

    public List<OrderItem> getProductsByOrder(Long orderId) throws MyEntityNotFoundException {
        Orderr order = orderBean.findOrFail(orderId);
        return order.getOrderItems();
    }

    public void delete(Long id) throws MyEntityNotFoundException {
        var order = findOrFail(id);
        if (order != null) {
            entityManager.remove(order);
        }
    }

    public Orderr find(Long orderId) throws MyEntityNotFoundException {
        return entityManager.find(Orderr.class, orderId);
    }

    public Orderr findOrFail(Long orderId) throws MyEntityNotFoundException {
        var order = find(orderId);
        if (order == null) {
            throw new MyEntityNotFoundException("Order with id '" + orderId + "' not found");
        }
        return order;
    }

    // Auxiliary: Find an existing product in the order to calculate the quantity
    private OrderItem findExistingItem(Orderr order, Product product) {
        for (OrderItem orderItem : order.getOrderItems()) {
            if (orderItem.getProduct().equals(product)) {
                return orderItem;
            }
        }
        return null;
    }

    public Orderr getOrderProducts(Long orderId) {
        Orderr order = entityManager.find(Orderr.class, orderId);
        if (order == null) throw new IllegalArgumentException("Order with id " + orderId + " not found");

        Hibernate.initialize(order.getOrderItems());

        if (order.getOrderItems().isEmpty()) throw new IllegalArgumentException("Order with id " + orderId + " has no products");

        return order;

    }
}
