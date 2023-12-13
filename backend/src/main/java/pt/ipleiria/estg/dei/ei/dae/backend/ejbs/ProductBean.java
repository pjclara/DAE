package pt.ipleiria.estg.dei.ei.dae.backend.ejbs;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.validation.ConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.Manufacturer;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.Product;
import pt.ipleiria.estg.dei.ei.dae.backend.exceptions.MyConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.backend.exceptions.MyEntityNotFoundException;

import java.util.List;

@Stateless
public class ProductBean {

    @PersistenceContext
    private EntityManager entityManager;

    public List<Product> all() {
        return entityManager.createNamedQuery("getAllProducts", Product.class).getResultList();
    }

    public void create(Long id, String name, int stock, String manufacturerUsername)
            throws MyConstraintViolationException, MyEntityNotFoundException {
        // if manufacturer exists
        Manufacturer manufacturer = entityManager.find(Manufacturer.class, manufacturerUsername);

        if (manufacturer == null) throw new MyEntityNotFoundException("Manufacturer with username " + null + " not found in database");

        try {
            Product product = new Product(id, name, stock, manufacturer);
            //entityManager.persist(product);
            manufacturer.addProduct(product);
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(e);
        }
    }

    public Product find(Long id) throws MyEntityNotFoundException {
        return entityManager.find(Product.class, id);
    }

    public void delete(Long id) throws MyEntityNotFoundException {
        var product = find(id);
        if (product != null) {
            entityManager.remove(product);
        }
    }

    public void update(Long id, String name, int stock, String manufacturerUsername)
    throws MyEntityNotFoundException{

        if (!exists(id)) {throw new MyEntityNotFoundException("Product with id " + id + " not found in database"); }

        Product product = entityManager.find(Product.class, id);
        entityManager.lock(product, LockModeType.OPTIMISTIC);

        product.setName(name);
        product.setStock(stock);

        if (manufacturerUsername != null && !manufacturerUsername.equals(product.getManufacturer().getUsername())) {
            Manufacturer manufacturer = entityManager.find(Manufacturer.class, manufacturerUsername);

            if (manufacturer == null) throw new MyEntityNotFoundException("Manufacturer with username " + manufacturerUsername + " not found in database");
            product.setManufacturer(manufacturer);
        }
        entityManager.merge(product);
    }

    public boolean exists(long id) {
        Query query = entityManager.createQuery("SELECT count (s.id) FROM Product s WHERE s.id = :id", Long.class);
        query.setParameter("id", id);
        return (Long) query.getSingleResult() > 0L;
    }

}
