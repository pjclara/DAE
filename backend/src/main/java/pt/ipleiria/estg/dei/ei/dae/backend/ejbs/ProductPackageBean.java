package pt.ipleiria.estg.dei.ei.dae.backend.ejbs;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import jakarta.validation.ConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.Package;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.PackagingType;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.ProductPackage;
import pt.ipleiria.estg.dei.ei.dae.backend.exceptions.MyConstraintViolationException;

@Stateless
public class ProductPackageBean {
    @PersistenceContext
    private EntityManager entityManager;

    public void create(PackagingType type, String material)  throws EntityExistsException, EntityNotFoundException, MyConstraintViolationException {
        try {
        var productPackage = new ProductPackage(
                type,
                material
        );
        entityManager.persist(productPackage);

        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(e);
        }
    }

    public Package find(long l) {
        return entityManager.find(Package.class, l);
    }
}
