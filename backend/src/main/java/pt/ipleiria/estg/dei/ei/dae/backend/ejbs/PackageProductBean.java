package pt.ipleiria.estg.dei.ei.dae.backend.ejbs;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import pt.ipleiria.estg.dei.ei.dae.backend.dtos.PackageProductDTO;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.PackageProduct;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.PackagingType;

import java.util.List;

@Stateless
public class PackageProductBean {

    @PersistenceContext
    private EntityManager entityManager;
    public List<PackageProduct> all() {
        return entityManager.createNamedQuery("getAllProductPackages", PackageProduct.class).getResultList();
    }

    public PackageProduct find(long id) {
        return entityManager.find(PackageProduct.class, id);
    }

    public void create(PackageProductDTO packageProductDTO) {

        var packageProduct = new PackageProduct(packageProductDTO.getPackagingType(), packageProductDTO.getPackagingMaterial());
        entityManager.persist(packageProduct);
    }

    public List<PackageProduct> getPackagesByType(String type) {
        return entityManager.createNamedQuery("getPackagesByType", PackageProduct.class).setParameter("type", PackagingType.valueOf(type)).getResultList();
    }
}
