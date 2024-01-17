package pt.ipleiria.estg.dei.ei.dae.backend.entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
@NamedQueries({
        @jakarta.persistence.NamedQuery(name = "getAllProductPackages", query = "SELECT pp FROM PackageProduct pp ORDER BY pp.id")
})
@NamedQuery(name = "getPackagesByType", query = "SELECT pp FROM PackageProduct pp WHERE pp.packagingType = :type")
public class PackageProduct extends Package {

    @OneToMany(mappedBy = "packageProduct", cascade = CascadeType.REMOVE)
    private List<Product> products;

    public PackageProduct() {
    }

    public PackageProduct(PackagingType packagingType, String packagingMaterial) {
        super(packagingType, packagingMaterial);
    }

    public void addProduct(Product product) {
        this.products.add(product);
        product.setPackageProduct(this);
    }

    public void removeProduct(Product product) {
        this.products.remove(product);
        product.setPackageProduct(null);
    }

    public Long getId() {
       return super.getId();
    }
}
