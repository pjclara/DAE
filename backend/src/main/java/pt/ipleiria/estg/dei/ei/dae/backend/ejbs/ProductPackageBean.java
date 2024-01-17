package pt.ipleiria.estg.dei.ei.dae.backend.ejbs;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import jakarta.validation.ConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.Package;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.PackageProduct;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.PackagingType;
import pt.ipleiria.estg.dei.ei.dae.backend.exceptions.MyConstraintViolationException;

@Stateless
public class ProductPackageBean {
    @PersistenceContext
    private EntityManager entityManager;

    public long create(PackagingType type, String material)  throws EntityExistsException, EntityNotFoundException, MyConstraintViolationException {

        PackageProduct PackageProduct = new PackageProduct(type, material);
        entityManager.persist(PackageProduct);

        if (PackageProduct == null) throw new EntityNotFoundException("Package with id " + PackageProduct.getId() + " not found in database");

        return PackageProduct.getId();

    }

    public Package find(long l) {
        return entityManager.find(Package.class, l);
    }
}
