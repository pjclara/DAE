package pt.ipleiria.estg.dei.ei.dae.backend.entities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@NamedQueries({
        @jakarta.persistence.NamedQuery(name = "getAllProductPackages", query = "SELECT pp FROM ProductPackage pp ORDER BY pp.id")
})
public class ProductPackage extends Package {

    public ProductPackage() {
    }

    public ProductPackage(PackagingType packagingType, String packagingMaterial) {
        super(packagingType, packagingMaterial);
    }

}
