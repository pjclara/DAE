package pt.ipleiria.estg.dei.ei.dae.backend.ejbs;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.hibernate.Hibernate;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.PackageSensor;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.Sensor;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.UnitProduct;

import java.util.List;
@Stateless
public class UnitProductBean {
    @PersistenceContext
    private EntityManager entityManager;

    @EJB
    private OrderBean orderBean;

   public List<UnitProduct> all() {
       List<UnitProduct> unitProducts =
               entityManager.createNamedQuery("getAllUnitProducts", UnitProduct.class).getResultList();
       unitProducts.forEach(unitProduct -> {
           Hibernate.initialize(unitProduct.getProduct());
           Hibernate.initialize(unitProduct.getProduct());
           if (unitProduct.getPackageSensor() != null) {
               Hibernate.initialize(unitProduct.getPackageSensor());
               if (unitProduct.getPackageSensor() != null)
                   Hibernate.initialize(unitProduct.getPackageSensor().getSensorValues());
           }
         });
        return unitProducts;
    }

    public UnitProduct getUnitProductByProductId(Long productId) {

        List<UnitProduct> unitProducts = entityManager.createNamedQuery("getUnitProductByProductId", UnitProduct.class)
                .setParameter("productId", productId)
                .getResultList();

        return unitProducts.get(0);
    }

    public UnitProduct find(long id) {
        UnitProduct unitProduct = entityManager.find(UnitProduct.class, id);

        if(unitProduct == null) throw new IllegalArgumentException("UnitProduct with id " + id + " not found in database");

        Hibernate.initialize(unitProduct.getProduct());
        Hibernate.initialize(unitProduct.getProduct());
        if (unitProduct.getPackageSensor() != null) {
            Hibernate.initialize(unitProduct.getPackageSensor());
            if (unitProduct.getPackageSensor() != null)
                Hibernate.initialize(unitProduct.getPackageSensor().getSensorValues());
        }

        return unitProduct;
    }

    public void update(long unitProductId, long packageSensorId) {
       UnitProduct u =  entityManager.find(UnitProduct.class, unitProductId);

       if(u == null) throw new IllegalArgumentException("UnitProduct with id " + unitProductId + " not found in database");

        PackageSensor pk = entityManager.find(PackageSensor.class, packageSensorId);

        if(pk == null) throw new IllegalArgumentException("PackageSensor with id " + packageSensorId + " not found in database");

        u.setPackageSensor(pk);

        entityManager.merge(u);
    }

    public List<Sensor> findSensorsNotAttribute(Long unitProductId) {
        UnitProduct unitProduct = entityManager.find(UnitProduct.class, unitProductId);

        if(unitProduct == null) throw new IllegalArgumentException("UnitProduct with id " + unitProductId + " not found in database");

        List<Sensor> sensors = entityManager.createNamedQuery("getSensorsNotAttribute", Sensor.class)
                .setParameter("productId", unitProduct.getProduct().getId())
                .getResultList();

        return sensors;
    }

    public Sensor findSensor(Long sensorId) {
        Sensor sensor = entityManager.find(Sensor.class, sensorId);

        if(sensor == null) throw new IllegalArgumentException("Sensor with id " + sensorId + " not found in database");

        return sensor;
    }

    public UnitProduct addSensorToUnitProduct(UnitProduct unitProduct, Sensor sensor) {
        unitProduct.addSensor(sensor);
        entityManager.merge(unitProduct);
        return unitProduct;
    }

    public UnitProduct findTheUnitProduct(Long unitProductId) {
        UnitProduct unitProduct = entityManager.find(UnitProduct.class, unitProductId);

        if(unitProduct == null) throw new IllegalArgumentException("UnitProduct with id " + unitProductId + " not found in database");

        return unitProduct;
    }

    public UnitProduct setSensorToTheUnitProduct(Long unitProductId, Long sensorId) {
       UnitProduct unitProduct = entityManager.find(UnitProduct.class, unitProductId);
       if(unitProduct == null) throw new IllegalArgumentException("UnitProduct with id " + unitProductId + " not found in database");

       Sensor sensor = entityManager.find(Sensor.class, sensorId);
       if(sensor == null) throw new IllegalArgumentException("Sensor with id " + sensorId + " not found in database");

       unitProduct.addSensor(sensor);

       entityManager.merge(unitProduct);

       return unitProduct;

    }
}
