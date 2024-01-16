package pt.ipleiria.estg.dei.ei.dae.backend.ejbs;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import pt.ipleiria.estg.dei.ei.dae.backend.dtos.PackageOrderDTO;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.PackageOrder;

import java.util.List;

@Stateless
public class PackageOrderBean {

    @PersistenceContext
    private EntityManager entityManager;

    public List<PackageOrder> all() {
        Query query = entityManager.createNamedQuery("getAllPackageOrders");
        return query.getResultList();
    }

    public long create(PackageOrderDTO packageOrderDTO) {
        PackageOrder packageOrder =
                new PackageOrder(packageOrderDTO.getPackagingType(), packageOrderDTO.getPackagingMaterial());
        entityManager.persist(packageOrder);

        return packageOrder.getId();
    }

    public PackageOrder find(long id) {
        return entityManager.find(PackageOrder.class, id);
    }

    public PackageOrder update(long id, PackageOrderDTO packageOrderDTO) {
        PackageOrder packageOrder = find(id);
        if (packageOrder == null) throw new IllegalArgumentException("There is no package with that id");

        if (packageOrder != null) {
            packageOrder.setPackagingType(packageOrderDTO.getPackagingType());
            packageOrder.setPackagingMaterial(packageOrderDTO.getPackagingMaterial());
            entityManager.merge(packageOrder);
        }
        return packageOrder;
    }

}
