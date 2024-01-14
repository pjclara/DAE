package pt.ipleiria.estg.dei.ei.dae.backend.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;

import java.util.List;

@Entity
@NamedQueries({
        @jakarta.persistence.NamedQuery(name = "getAllPackageOfPackages", query = "SELECT pp FROM Package pp ORDER BY pp.id")
})
public class PackageOfPackage extends Package {

        public PackageOfPackage() {
        }

        public PackageOfPackage(PackagingType packagingType, String packagingMaterial, int numberOfPackages) {
            super(packagingType, packagingMaterial);
        }





}
