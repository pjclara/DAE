package pt.ipleiria.estg.dei.ei.dae.backend.ejbs;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.persistence.*;
import jakarta.validation.ConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.Package;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.PackagingType;
import pt.ipleiria.estg.dei.ei.dae.backend.exceptions.MyConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.backend.exceptions.MyEntityNotFoundException;

import java.util.List;

@Stateless
public class PackageBean {

    @PersistenceContext
    private EntityManager entityManager;

    @EJB
    private SensorBean sensorBean;

     public void create(Long id, PackagingType type, String material)
            throws EntityExistsException, EntityNotFoundException, MyConstraintViolationException {

        try {
            var package_ = new Package(id, type, material);
            entityManager.persist(package_);
            entityManager.flush();
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(e);
        }
    }

    public List<Package> all() {
        return entityManager.createNamedQuery("getAllPackages", Package.class).getResultList();
    }

    public static final List<PackagingType> LOGISTICS_TYPES = List.of(
            PackagingType.SECONDARY,
            PackagingType.THIRD,
            PackagingType.TRANSPORT
    );

    public static final List<PackagingType> MANUFACTURER_TYPES = List.of(
            PackagingType.PRIMARY,
            PackagingType.SECONDARY
    );

    public List<Package> getAllPackagesByRole(String userRole) {

        List<PackagingType> role = null;
        if ("LogisticsOperator".equals(userRole)) {
            role = LOGISTICS_TYPES;

        } else if ("Manufacturer".equals(userRole)) {
            role = MANUFACTURER_TYPES;
        }
        return entityManager.createNamedQuery("getAllRoleTypePackages", Package.class)
                .setParameter("rolesTypes", role)
                .getResultList();
    }

    public Package find(Long packageId) {
        return entityManager.find(Package.class, packageId);
    }

    public Package findOrFail(Long packageId) throws MyEntityNotFoundException {
        var package_ = find(packageId);
        if (package_ == null) {
            throw new MyEntityNotFoundException("Package with code '" + packageId + "' not found");
        }
        return package_;
    }

    public void update(Long id, PackagingType type, String material) throws MyEntityNotFoundException {
        var package_ = findOrFail(id);

        entityManager.lock(package_, LockModeType.OPTIMISTIC);

        package_.setPackagingType(type);
        package_.setPackagingMaterial(material);
    }

    public void addSensorToPackage(Long packageId, Long sensorId) throws MyEntityNotFoundException {
        Package package_ = findOrFail(packageId);
        var sensorToAdd = sensorBean.find(sensorId);
        if (sensorToAdd == null) {
            throw new MyEntityNotFoundException("Sensor with id '" + sensorId + "' not found");
        }
        package_.addSensor(sensorToAdd);
        entityManager.merge(package_);
    }

    public void removeSensorFromPackage(Long packageId, Long sensorId) throws MyEntityNotFoundException {
        Package package_ = findOrFail(packageId);
        var sensorToRemove = sensorBean.find(sensorId);
        if (sensorToRemove == null) {
            throw new MyEntityNotFoundException("Sensor with id '" + sensorId + "' not found");
        }
        package_.removeSensor(sensorToRemove);
        entityManager.merge(package_);
    }

    public void remove(Long id) throws MyEntityNotFoundException {
        var product = find(id);
        if (product != null) {
            entityManager.remove(product);
        }
    }

    public List<Package> getPackageByType(PackagingType type) {
        return entityManager.createNamedQuery("getPackageByType", Package.class)
                .setParameter("packagingType", type)
                .getResultList();
    }
}
