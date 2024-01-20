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
import pt.ipleiria.estg.dei.ei.dae.backend.exceptions.MyConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.backend.exceptions.MyEntityNotFoundException;

import java.io.StringReader;
import java.util.List;
import java.util.stream.Collectors;

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

        hibernateOrderItems(order);
        return order;

    }

    public List<Orderr> getAll() {
        List<Orderr> orders = entityManager.createNamedQuery("getOrdersWithOrderItems", Orderr.class).getResultList();

        orders.forEach(orderr -> {
            HibernateOrders(orderr);
        });
        return orders;

    }


    public long updateStatus(Long id, String status) {
        Orderr order = entityManager.find(Orderr.class, id);
        if (order == null) throw new IllegalArgumentException("Order with id " + id + " not found");

        order.setStatus(status);
        entityManager.merge(order);

        HibernateOrders(order);

        return order.getId();
    }

    private void HibernateOrders(Orderr order) {
        Hibernate.initialize(order.getOrderItems());
        hibernateOrderItems(order);
    }

    private void hibernateOrderItems(Orderr order) {
        order.getOrderItems().forEach(orderItem -> {
            Hibernate.initialize(orderItem.getUnitProduct());
            if (orderItem.getUnitProduct().getPackageSensor() != null) {
                Hibernate.initialize(orderItem.getUnitProduct().getPackageSensor());
                if (orderItem.getUnitProduct().getPackageSensor().getSensorValues() != null)
                    Hibernate.initialize(orderItem.getUnitProduct().getPackageSensor().getSensorValues());
            }
        });
    }

    public long addSensorToPackageOrder(Long id, Long sensorId) {
        Orderr order = entityManager.find(Orderr.class, id);
        if (order == null) throw new IllegalArgumentException("Order with id " + id + " not found");

        Sensor sensor = entityManager.find(Sensor.class, sensorId);
        if (sensor == null) throw new IllegalArgumentException("Sensor with id " + sensorId + " not found");

        PackageSensor packageSensor = order.getPackageSensor();
        if (packageSensor == null) throw new IllegalArgumentException("Order with id " + id + " has no package");

        packageSensor.getSensorValues().forEach(sensorValue -> {
            if (sensorValue.getSensor().getId() == sensor.getId()) {
                throw new IllegalArgumentException("Order with id " + id + " already has sensor with id " + sensorId);
            }
        });

        SensorValue sensorValue = new SensorValue(sensor, packageSensor);
        entityManager.persist(sensorValue);

        return order.getId();
    }

    public List<Sensor> getSensorsNotInOrder(Long orderId) {
        Orderr order = entityManager.find(Orderr.class, orderId);
        if (order == null) throw new IllegalArgumentException("Order with id " + orderId + " not found");

        List<Sensor> sensors = entityManager.createNamedQuery("getSensorsBySource", Sensor.class)
                .setParameter("source", "Orders")
                .getResultList();

        // sensors.removeIf(sensor ->
        //         order.getPackageSensor().getSensorValues().stream().anyMatch(sensorValue ->
        //                 sensorValue.getSensor().getId() != sensor.getId()));
        sensors = sensors.stream().filter(sensor ->
                order.getPackageSensor().getSensorValues().stream().noneMatch(sensorValue ->
                        sensorValue.getSensor().getId().equals(sensor.getId()))).collect(Collectors.toList());

        return sensors;

    }

    public List<Sensor> getSensorsInOrder(Long orderId) {
        Orderr order = entityManager.find(Orderr.class, orderId);
        if (order == null) throw new IllegalArgumentException("Order with id " + orderId + " not found");

        Hibernate.initialize(order.getPackageSensor());
        if (order.getPackageSensor() == null) throw new IllegalArgumentException("Order with id " + orderId + " has no package");

        Hibernate.initialize(order.getPackageSensor().getSensorValues());

        if (order.getPackageSensor().getSensorValues().isEmpty())
            return null;

        return order.getPackageSensor().getSensorValues().stream().map(SensorValue::getSensor).toList();

    }
}
