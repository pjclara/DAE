package pt.ipleiria.estg.dei.ei.dae.backend.ejbs;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.LockModeType;
import jakarta.persistence.PersistenceContext;
import jakarta.validation.ConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.*;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.Package;
import pt.ipleiria.estg.dei.ei.dae.backend.exceptions.MyConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.backend.exceptions.MyEntityNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class OrderBean {

    @PersistenceContext
    private EntityManager entityManager;

    @EJB
    private OrderBean orderBean;

    @EJB
    private ProductBean productBean;

    public List<Orderr> all() {
        return entityManager.createNamedQuery("getAllOrders", Orderr.class).getResultList();
    }

    public List<Orderr> getOrdersByEndConsumer(String customerName) {
        return entityManager.createNamedQuery("getOrdersByEndConsumer", Orderr.class)
                .setParameter("endConsumerUsername", customerName)
                .getResultList();
    }

    public long create(String status, String endConsumerUsername,  List<Long> productIds) throws MyEntityNotFoundException, MyConstraintViolationException {
        // check logisticOpt exists

        // check endCostumer
        EndConsumer endConsumer = entityManager.find(EndConsumer.class, endConsumerUsername);
        if (endConsumer == null) throw new IllegalArgumentException("End Consumer with username " + endConsumerUsername + " not found");
/*
        LogisticsOperator logisticsOperator = null;
        if(logisticsOperatorName != null){
            logisticsOperator = entityManager.find(LogisticsOperator.class, logisticsOperatorName);
            // if (logisticsOperator == null) throw new IllegalArgumentException("Logistics Operator with username " + logisticsOperatorName + " not found");
        }
    */
        // check if order already exists
        //Orderr order = entityManager.find(Orderr.class, id);
        //if (order!= null){ throw new EntityNotFoundException("Orderr with id '" + id + "' already exists"); }
        try {
            Orderr order = new Orderr(status, endConsumer);

            for (Long productId : productIds) {
                System.out.println("Adding Product: " + productId);

                Product product = productBean.find(productId);
                if (product == null) {
                    throw new IllegalArgumentException("Product with id " + productId + " not found");
                }

                // Check if same product already exists
                OrderItem existingProduct = findExistingItem(order, product);
                if (existingProduct != null) {
                    // If exists, increment the quantity
                    existingProduct.setQuantity(existingProduct.getQuantity() + 1);
                } else {
                    // If not exists, create a new OrderItem
                    OrderItem orderItem = new OrderItem();
                    orderItem.setProduct(product);
                    orderItem.setQuantity(1); // initially the quantity is always 1

                    order.addOrderItem(orderItem);
                }

                OrderItem orderItem = new OrderItem();
                orderItem.setProduct(product);
                orderItem.setQuantity(1); // --------- validate this
                order.addOrderItem(orderItem);
            }

            entityManager.persist(order);
            System.out.println("add order to endConsumer");
            endConsumer.addOrder(order);
            return order.getId().intValue();
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(e);
        }

/*
        for (Long productId : productIds) {
            System.out.println("Adding Product: " + productId);

            Product product = productBean.find(productId);
            if (product == null) {
                throw new IllegalArgumentException("Product with id " + productId + " not found");
            }

            // Check if same product already exists
            OrderItem existingProduct = findExistingItem(order, product);
            if (existingProduct != null) {
                // If exists, increment the quantity
                existingProduct.setQuantity(existingProduct.getQuantity() + 1);
            } else {
                // If not exists, create a new OrderItem
                OrderItem orderItem = new OrderItem();
                orderItem.setProduct(product);
                orderItem.setQuantity(1); // initially the quantity is always 1

                order.addOrderItem(orderItem);
            }

            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(product);
            orderItem.setQuantity(1); // --------- validate this
            order.addOrderItem(orderItem);


        }

        entityManager.persist(order);
        System.out.println("add order to endConsumer");
        endConsumer.addOrder(order);
        System.out.println("add order to logisticOpt");
        //logisticOpt.addOrder(order);
        */

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

}
