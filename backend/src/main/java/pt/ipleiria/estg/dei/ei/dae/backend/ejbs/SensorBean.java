package pt.ipleiria.estg.dei.ei.dae.backend.ejbs;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.validation.ConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.backend.dtos.SensorDTO;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.Manufacturer;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.Package;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.Product;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.Sensor;
import pt.ipleiria.estg.dei.ei.dae.backend.exceptions.MyConstraintViolationException;

import java.util.List;

@Stateless
public class SensorBean {
    @PersistenceContext
    private EntityManager entityManager;

    public void create(long id, String source, String type, String value, String unit, String max, String min, Long timestamp) {

        var sensor = new Sensor(id, source, type, value, unit, max, min, timestamp);
        entityManager.persist(sensor);
    }

    public Sensor find(long id) {
        return entityManager.find(Sensor.class, id);
    }

    public List<Sensor> getAll() {
        return entityManager.createNamedQuery("getAllSensors", Sensor.class).getResultList();
    }

    public void delete(long id) {
        var sensor = find(id);
        if (sensor != null) {
            entityManager.remove(sensor);
        }
    }

    public void update(long id, String source, String type, String value, String unit, String max, String min, Long timestamp, long packageId) throws MyConstraintViolationException {
        Package packagging = entityManager.find(Package.class, packageId);

        if (packagging == null) throw new IllegalArgumentException("Package with id: " + packageId + " not found in database");

        var sensor = find(id);

        try {
            sensor.setSource(source);
            sensor.setType(type);
            sensor.setValue(value);
            sensor.setUnit(unit);
            sensor.setMax(max);
            sensor.setMin(min);
            sensor.setTimestamp(timestamp);
            sensor.setPackagging(packagging);
            entityManager.merge(sensor);
        } catch (ConstraintViolationException e) {
        throw new MyConstraintViolationException(e);
    }
    }
}
