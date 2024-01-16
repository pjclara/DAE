package pt.ipleiria.estg.dei.ei.dae.backend.ejbs;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.LockModeType;
import jakarta.persistence.PersistenceContext;
import org.hibernate.Hibernate;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.*;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.Package;
import pt.ipleiria.estg.dei.ei.dae.backend.exceptions.MyConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.backend.exceptions.MyEntityNotFoundException;

import java.io.StringReader;
import java.util.List;

@Stateless
public class OrderBean {

    @PersistenceContext
    private EntityManager entityManager;

    @EJB
    private OrderBean orderBean;

    @EJB
    private UnitProductBean unitProductBean;


    public List<Orderr> all() {
        return entityManager.createNamedQuery("getOrdersWithOrderItems", Orderr.class).getResultList();
    }

    public List<Orderr> getOrdersByEndConsumer(String customerName) {
        return entityManager.createNamedQuery("getOrdersByEndConsumer", Orderr.class)
                .setParameter("endConsumerUsername", customerName)
                .getResultList();
    }

    public long create(String endConsumerUsername, String data) throws MyEntityNotFoundException, MyConstraintViolationException {

        try(JsonReader jsonReader = Json.createReader(new StringReader(data))){

            JsonObject jsonObject = jsonReader.readObject();

            String status = jsonObject.getString("status");

            JsonArray orderItems = jsonObject.getJsonArray("orderItems");

            // check endCostumer
            EndConsumer endConsumer = entityManager.find(EndConsumer.class, endConsumerUsername);
            if (endConsumer == null) throw new IllegalArgumentException("End Consumer with username " + endConsumerUsername + " not found");

            // create order
            Orderr order = new Orderr(status, endConsumer);
            entityManager.persist(order);

            // add order items
            for (int i = 0; i < orderItems.size(); i++) {
                JsonObject orderItem = orderItems.getJsonObject(i);
                Long productId = orderItem.getJsonNumber("productId").longValue();
                int quantity = orderItem.getInt("quantity");

                Product product = entityManager.find(Product.class, productId);
                if (product == null) throw new IllegalArgumentException("Product with id " + productId + " not found");

                product.setStock(product.getStock() - quantity);
                entityManager.merge(product);

                UnitProduct unitProduct = unitProductBean.getUnitProductByProductId(productId);
                unitProduct.setAvailable(false);
                entityManager.merge(unitProduct);

                OrderItem orderItem1 = new OrderItem(unitProduct, quantity, order);
                orderItem1.setOrderr(order);
                entityManager.persist(orderItem1);
            }

            return order.getId();
        }catch (Exception e){


            return -1;
        }


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
            PackageOrder packageOrder = entityManager.find(PackageOrder.class, packageId);
            if (packageOrder == null) throw new IllegalArgumentException("Package with id " + packageId + " not found");
            order.setPackageOrder(packageOrder);
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


    public Orderr getOrderProducts(Long orderId) {
        Orderr order = entityManager.find(Orderr.class, orderId);
        if (order == null) throw new IllegalArgumentException("Order with id " + orderId + " not found");

        Hibernate.initialize(order.getOrderItems());

        if (order.getOrderItems().isEmpty()) throw new IllegalArgumentException("Order with id " + orderId + " has no products");

        order.getOrderItems().forEach(orderItem -> {
            Hibernate.initialize(orderItem.getUnitProduct());
            Hibernate.initialize(orderItem.getUnitProduct().getPackageSensor());
            Hibernate.initialize(orderItem.getUnitProduct().getPackageSensor().getSensorValues());
        });
        return order;

    }

    public void addProductToOrder(long order, int i, int i1) throws MyEntityNotFoundException {


    }

    public List<Orderr> getAll() {
        List<Orderr> orders = entityManager.createNamedQuery("getOrdersWithOrderItems", Orderr.class).getResultList();

        orders.forEach(orderr -> {
            Hibernate.initialize(orderr.getOrderItems());
            orderr.getOrderItems().forEach(orderItem -> {
                Hibernate.initialize(orderItem.getUnitProduct());
                Hibernate.initialize(orderItem.getUnitProduct().getPackageSensor());
                Hibernate.initialize(orderItem.getUnitProduct().getPackageSensor().getSensorValues());
            });
        });
        return orders;

    }

    public void updateOrderPackage(Long id, Long packageId) {
        Orderr order = entityManager.find(Orderr.class, id);
        if (order == null) throw new IllegalArgumentException("Order with id " + id + " not found");

        PackageOrder packageOrder = entityManager.find(PackageOrder.class, packageId);
        if (packageOrder == null) throw new IllegalArgumentException("Package with id " + packageId + " not found");

        order.setPackageOrder(packageOrder);
        entityManager.merge(order);

    }
}
