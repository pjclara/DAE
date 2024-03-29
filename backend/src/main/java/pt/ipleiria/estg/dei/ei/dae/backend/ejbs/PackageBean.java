package pt.ipleiria.estg.dei.ei.dae.backend.ejbs;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.persistence.*;
import jakarta.validation.ConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.Package;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.PackagingType;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.Sensor;
import pt.ipleiria.estg.dei.ei.dae.backend.exceptions.MyConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.backend.exceptions.MyEntityNotFoundException;

import java.util.ArrayList;
import java.util.List;

@Stateless
public class PackageBean {

    @PersistenceContext
    private EntityManager entityManager;

    @EJB
    private SensorBean sensorBean;

    @EJB
    private PackageBean packageBean;

     public long create(PackagingType type, String material)
            throws EntityExistsException, EntityNotFoundException, MyConstraintViolationException {
        try {
            Package package_ = new Package(type, material);
            entityManager.persist(package_);
            return package_.getId().intValue();
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
        System.out.println("--------- User Role: " + userRole);

        if ("logisticsOperator".equals(userRole)) {
            return entityManager.createNamedQuery("getAllRoleTypePackages", Package.class)
                    .setParameter("rolesTypes", LOGISTICS_TYPES)
                    .getResultList();

        }
        if ("manufacturer".equals(userRole)) {
            return entityManager.createNamedQuery("getAllRoleTypePackages", Package.class)
                    .setParameter("rolesTypes", MANUFACTURER_TYPES)
                    .getResultList();
        } else {
            return entityManager.createNamedQuery("getAllPackages", Package.class).getResultList();
        }

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


    public void removeSensorFromPackage(Long packageId, Long sensorId) throws MyEntityNotFoundException {
        Package package_ = findOrFail(packageId);
        var sensorToRemove = sensorBean.find(sensorId);
        if (sensorToRemove == null) {
            throw new MyEntityNotFoundException("Sensor with id '" + sensorId + "' not found");
        }
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
