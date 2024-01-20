package pt.ipleiria.estg.dei.ei.dae.backend.ejbs;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.validation.ConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.backend.dtos.SensorDTO;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.*;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.Package;
import pt.ipleiria.estg.dei.ei.dae.backend.exceptions.MyConstraintViolationException;

import java.util.List;

@Stateless
public class SensorValueBean {
    @PersistenceContext
    private EntityManager entityManager;

    public void update(long id, String value) throws MyConstraintViolationException {
        SensorValue sensorValue = entityManager.find(SensorValue.class, id);
        if (sensorValue == null) throw new IllegalArgumentException("SensorValue with id " + id + " not found in database");

        try {
            sensorValue.setValue(value);
            entityManager.merge(sensorValue);
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(e);
        }
    }

    public SensorValue find(long id) {
        return entityManager.find(SensorValue.class, id);
    }

}
