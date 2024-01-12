package pt.ipleiria.estg.dei.ei.dae.backend.ejbs;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
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
}
