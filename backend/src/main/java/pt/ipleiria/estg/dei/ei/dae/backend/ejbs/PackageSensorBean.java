package pt.ipleiria.estg.dei.ei.dae.backend.ejbs;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.Package;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.PackageSensor;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.Sensor;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.UnitProduct;

import java.util.List;

@Stateless
public class PackageSensorBean {

    @PersistenceContext
    private EntityManager entityManager;

    public List<PackageSensor> all() {
        return entityManager.createNamedQuery("getAllPackageSensors", PackageSensor.class).getResultList();
    }

    public void create(long sensorId, long packageId, long unitProductId, String value){
        Sensor sensor = entityManager.find(Sensor.class,sensorId );

        if(sensor == null) throw new IllegalArgumentException("Sensor with id " + sensorId + " not found in database");

        Package aPackage = entityManager.find(Package.class, packageId);

        if(aPackage == null) throw new IllegalArgumentException("Package with id " + packageId + " not found in database");

        UnitProduct unitProduct = entityManager.find(UnitProduct.class, unitProductId);

        if(unitProduct == null) throw new IllegalArgumentException("UnitProduct with id " + unitProductId + " not found in database");

        List<UnitProduct> unitProducts = List.of(unitProduct);
        PackageSensor packageSensor = new PackageSensor(sensor, aPackage, unitProducts, value);

        entityManager.persist(packageSensor);
    }

    public void addUnitProductToPackage(long packageSensorId, long unitProductId){
        PackageSensor packageSensor = entityManager.find(PackageSensor.class, packageSensorId);

        if(packageSensor == null) throw new IllegalArgumentException("PackageSensor with id " + packageSensorId + " not found in database");

        UnitProduct unitProduct = entityManager.find(UnitProduct.class, unitProductId);

        if(unitProduct == null) throw new IllegalArgumentException("UnitProduct with id " + unitProductId + " not found in database");

        packageSensor.getUnitProducts().add(unitProduct);

        entityManager.merge(packageSensor);

    }

}
