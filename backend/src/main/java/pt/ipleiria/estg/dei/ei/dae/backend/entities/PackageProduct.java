package pt.ipleiria.estg.dei.ei.dae.backend.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;

@Entity
@NamedQueries({
        @jakarta.persistence.NamedQuery(name = "getAllProductPackages", query = "SELECT pp FROM PackageProduct pp ORDER BY pp.id")
})
@NamedQuery(name = "getPackagesByType", query = "SELECT pp FROM PackageProduct pp WHERE pp.packagingType = :type")
public class PackageProduct extends Package {

    public PackageProduct() {
    }

    public PackageProduct(PackagingType packagingType, String packagingMaterial) {
        super(packagingType, packagingMaterial);
    }
}
