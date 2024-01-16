package pt.ipleiria.estg.dei.ei.dae.backend.ejbs;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.validation.ConstraintViolationException;
import org.hibernate.Hibernate;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.*;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.Package;
import pt.ipleiria.estg.dei.ei.dae.backend.exceptions.MyConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.backend.exceptions.MyEntityNotFoundException;

import java.util.List;
import java.util.UUID;

@Stateless
public class ProductBean {

    @PersistenceContext
    private EntityManager entityManager;

    public List<Product> all() {
        List<Product> products = entityManager.createNamedQuery("getAllProducts", Product.class).getResultList();
        products.forEach(product -> {
            Hibernate.initialize(product.getManufacturer());
            Hibernate.initialize(product.getUnitProducts());
        });
        return products;
    }

    public long create(String name, int stock, String image, String manufacturerUsername, long packageProductId)
            throws MyConstraintViolationException, MyEntityNotFoundException {
        // if manufacturer exists
        Manufacturer manufacturer = entityManager.find(Manufacturer.class, manufacturerUsername);

        if (manufacturer == null) throw new MyEntityNotFoundException("Manufacturer with username " + null + " not found in database");

        try {
            Product product = new Product(name, stock, image, manufacturer);
            entityManager.persist(product);
            manufacturer.addProduct(product);

            PackageProduct productPackage = null;
            if(packageProductId != 0) {
                productPackage = entityManager.find(PackageProduct.class, packageProductId);
                if (productPackage == null)
                    throw new MyEntityNotFoundException("Package with id " + packageProductId + " not found in database");
            }

            for (int i = 0; i < stock; i++) {
                UnitProduct unitProduct = new UnitProduct(product, UUID.randomUUID(), true, null);
                entityManager.persist(unitProduct);
                if (productPackage != null) {
                    PackageSensor packageSensor = new PackageSensor(productPackage, unitProduct);
                    entityManager.persist(packageSensor);
                    unitProduct.setPackageSensor(packageSensor);
                    entityManager.merge(unitProduct);
                }
            }

            return product.getId().intValue();
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(e);
        }
    }
    public Product find(Long id) throws MyEntityNotFoundException {
        Product product = entityManager.find(Product.class, id);
        if (product == null) throw new MyEntityNotFoundException("Product with id " + id + " not found in database");
        Hibernate.initialize(product.getManufacturer());
        Hibernate.initialize(product.getUnitProducts());

        return entityManager.find(Product.class, id);
    }

    public void delete(Long id) throws MyEntityNotFoundException {
        var product = find(id);
        if (product != null) {
            entityManager.remove(product);
        }
    }

    public void update(Long id, String name, int stock, String manufacturerUsername, String image)
    throws MyEntityNotFoundException{

        if (!exists(id)) {throw new MyEntityNotFoundException("Product with id " + id + " not found in database"); }

        Product product = entityManager.find(Product.class, id);
        entityManager.lock(product, LockModeType.OPTIMISTIC);

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

        if (product == null) throw new IllegalArgumentException("Product with id " + id + " not found");

        Hibernate.initialize(product.getManufacturer());
        Hibernate.initialize(product.getUnitProducts());

        product.getUnitProducts().forEach(unitProduct ->
                {
                    Hibernate.initialize(unitProduct.getPackageSensor());
                    if (unitProduct.getPackageSensor() != null)
                        Hibernate.initialize(unitProduct.getPackageSensor().getSensorValues());
                }
        );
        return product;
    }

    public void setPackaging(Long id, Long packageId) {
        Product product = entityManager.find(Product.class, id);
        if (product == null) throw new IllegalArgumentException("Product with id " + id + " not found");

        Package productPackage = entityManager.find(Package.class, packageId);
        if (productPackage == null) throw new IllegalArgumentException("Package with id " + packageId + " not found");

        // FIND ALL UNIT PRODUCTS
        Query query = entityManager.createQuery("SELECT s FROM UnitProduct s WHERE s.product.id = :id", UnitProduct.class);
        query.setParameter("id", id);

        List<UnitProduct> unitProducts = query.getResultList();


        if (unitProducts.isEmpty()) throw new IllegalArgumentException("Product with id " + id + " has no unit products");


        // SET PACKAGING
        for (UnitProduct unitProduct : unitProducts) {
            PackageSensor packageSensor = new PackageSensor(productPackage, unitProduct);
            entityManager.persist(packageSensor);

            unitProduct.setPackageSensor(packageSensor);
            entityManager.merge(unitProduct);
        }


    }

    public List<UnitProduct> getAllUnitProducts(Long id) {
        Product product = entityManager.find(Product.class, id);
        if (product == null) throw new IllegalArgumentException("Product with id " + id + " not found");

        List<UnitProduct> unitProducts = product.getUnitProducts();

        if (unitProducts.isEmpty()) throw new IllegalArgumentException("Product with id " + id + " has no unit products");

        unitProducts.forEach(unitProduct ->
                {
                    Hibernate.initialize(unitProduct.getPackageSensor());
                    if (unitProduct.getPackageSensor() != null)
                        Hibernate.initialize(unitProduct.getPackageSensor().getSensorValues());
                }
        );

        return unitProducts;
    }
}
