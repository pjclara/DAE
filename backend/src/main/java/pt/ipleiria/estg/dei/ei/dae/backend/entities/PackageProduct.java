package pt.ipleiria.estg.dei.ei.dae.backend.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.NamedQueries;

@Entity
@NamedQueries({
        @jakarta.persistence.NamedQuery(name = "getAllProductPackages", query = "SELECT pp FROM PackageProduct pp ORDER BY pp.id")
})
public class PackageProduct extends Package {

    public PackageProduct() {
    }

    public PackageProduct(PackagingType packagingType, String packagingMaterial) {
        super(packagingType, packagingMaterial);
    }
}
