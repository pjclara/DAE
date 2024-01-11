package pt.ipleiria.estg.dei.ei.dae.backend.ejbs;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.validation.ConstraintViolationException;
import org.hibernate.Hibernate;
import pt.ipleiria.estg.dei.ei.dae.backend.dtos.PackageDTO;
import pt.ipleiria.estg.dei.ei.dae.backend.dtos.ProductDTO;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.Manufacturer;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.Package;
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

    public long create(String name, int stock, String image,  String manufacturerUsername, long p)
            throws MyConstraintViolationException, MyEntityNotFoundException {
        // if manufacturer exists
        Manufacturer manufacturer = entityManager.find(Manufacturer.class, manufacturerUsername);

        if (manufacturer == null) throw new MyEntityNotFoundException("Manufacturer with username " + null + " not found in database");

        Package productPackage = entityManager.find(Package.class, p);

        try {
            Product product = new Product(name, stock, image, productPackage, manufacturer);
            entityManager.persist(product);
            manufacturer.addProduct(product);
            productPackage.addProduct(product);
            return product.getId().intValue();
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

    public void update(Long id, String name, int stock, String manufacturerUsername, String image, PackageDTO packageDTO)
    throws MyEntityNotFoundException{

        if (!exists(id)) {throw new MyEntityNotFoundException("Product with id " + id + " not found in database"); }

        Product product = entityManager.find(Product.class, id);
        entityManager.lock(product, LockModeType.OPTIMISTIC);

        Package productPackage = entityManager.find(Package.class, packageDTO.getId());

        if (productPackage == null) throw new MyEntityNotFoundException("Package with id " + packageDTO.getId() + " not found in database");

        product.setProductPackage(productPackage);
        product.setName(name);
        product.setStock(stock);
        product.setImage(image);

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

    public Product findWithPackage(Long id) {
        Product product = entityManager.find(Product.class, id);

        if (product == null) return null;

        Hibernate.initialize(product.getProductPackage());

        Hibernate.initialize(product.getManufacturer());

        return product;
    }
}
