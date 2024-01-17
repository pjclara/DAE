package pt.ipleiria.estg.dei.ei.dae.backend.entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
@NamedQuery(name = "getAllPackageOrders", query = "SELECT pp FROM PackageOrder pp ORDER BY pp.id")
public class PackageOrder extends Package {

    public PackageOrder() {
    }

    public PackageOrder(PackagingType packagingType, String packagingMaterial) {
        super(packagingType, packagingMaterial);
    }
}
