package pt.ipleiria.estg.dei.ei.dae.backend.ejbs;

import com.sun.source.tree.IfTree;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.hibernate.Hibernate;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.PackageSensor;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.UnitProduct;

import java.util.List;
@Stateless
public class UnitProductBean {
    @PersistenceContext
    private EntityManager entityManager;

    @EJB
    private OrderBean orderBean;

   public List<UnitProduct> all() {
        return entityManager.createNamedQuery("getAllUnitProducts", UnitProduct.class).getResultList();
    }

    public UnitProduct getUnitProductByProductId(Long productId) {

        List<UnitProduct> unitProducts = entityManager.createNamedQuery("getUnitProductByProductId", UnitProduct.class)
                .setParameter("productId", productId)
                .getResultList();

        return unitProducts.get(0);
    }

    public UnitProduct find(long id) {
        UnitProduct unitProduct = entityManager.find(UnitProduct.class, id);

        if(unitProduct == null) throw new IllegalArgumentException("UnitProduct with id " + id + " not found in database");

        Hibernate.initialize(unitProduct.getProduct());
        Hibernate.initialize(unitProduct.getProduct());
        if (unitProduct.getPackageSensor() != null) {
            Hibernate.initialize(unitProduct.getPackageSensor());
            if (unitProduct.getPackageSensor() != null)
                Hibernate.initialize(unitProduct.getPackageSensor().getSensorValues());
        }

        return unitProduct;
    }

    public void update(long unitProductId, long packageSensorId) {
       UnitProduct u =  entityManager.find(UnitProduct.class, unitProductId);

       if(u == null) throw new IllegalArgumentException("UnitProduct with id " + unitProductId + " not found in database");

        PackageSensor pk = entityManager.find(PackageSensor.class, packageSensorId);

        if(pk == null) throw new IllegalArgumentException("PackageSensor with id " + packageSensorId + " not found in database");

        u.setPackageSensor(pk);

        entityManager.merge(u);
    }
}
