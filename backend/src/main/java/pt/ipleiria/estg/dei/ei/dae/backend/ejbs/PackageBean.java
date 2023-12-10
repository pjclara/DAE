package pt.ipleiria.estg.dei.ei.dae.backend.ejbs;

import jakarta.ejb.Stateless;
import jakarta.persistence.*;
import jakarta.validation.ConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.Package;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.Sensor;
import pt.ipleiria.estg.dei.ei.dae.backend.exceptions.MyConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.backend.exceptions.MyEntityNotFoundException;

import java.util.List;

@Stateless
public class PackageBean {

    @PersistenceContext
    private EntityManager entityManager;

     public void create(Long id, String type, String material)
            throws EntityExistsException, EntityNotFoundException, MyConstraintViolationException {

        try {
            var package_ = new Package(id, type, material);
            entityManager.persist(package_);
            entityManager.flush(); // when using Hibernate, to force it to throw a ContraintViolationException, as in the JPA specification
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(e);
        }
    }

    public List<Package> all() {
        return entityManager.createNamedQuery("getAllPackages", Package.class).getResultList();
    }

    public Package find(Long packageId) {
        return entityManager.find(Package.class, packageId);
    }

    public Package findOrFail(long packageCode) throws MyEntityNotFoundException {
        var package_ = find(packageCode);
        if (package_ == null) {
            throw new MyEntityNotFoundException("Package with code '" + packageCode + "' not found");
        }
        return package_;
    }

    public void update(long code, String type, String material) throws MyEntityNotFoundException {
        var package_ = findOrFail(code);

        entityManager.lock(package_, LockModeType.OPTIMISTIC);

        package_.setPackagingType(type);
        package_.setPackagingMaterial(material);
        // ver sobre os sensores -- ver se existe, se nao existir adicionar -- ver codigo abaixo..?

        // a "lazy way" that avoids querying the course every time we do an update to the student
        /*if (!Objects.equals(student.getCourse().getCode(), courseCode)) {
            student.setCourse(courseBean.findOrFail(courseCode));
        }*/
    }

    public void remove(Long packageId) throws MyEntityNotFoundException {
        var product = find(packageId);
        if (product != null) {
            entityManager.remove(product);
        }
    }

}
