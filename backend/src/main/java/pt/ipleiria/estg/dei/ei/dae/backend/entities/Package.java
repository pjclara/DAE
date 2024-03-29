package pt.ipleiria.estg.dei.ei.dae.backend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;

import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "packages")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@NamedQueries({
        @NamedQuery(name = "getPackageByType", query = "SELECT p FROM Package p WHERE p.packagingType = :packagingType"),
        @NamedQuery(name = "getAllPackages", query = "SELECT p FROM Package p ORDER BY p.id")
})
public class Package extends Versionable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "TBL_METADATA_ID_SEQ")
    @Column(name="id")
    private Long id;
    @NotNull
    private PackagingType packagingType;  // [1º,2º,3º(Produto) ou encomenda / transporte]
    @NotNull
    private String packagingMaterial;

    @OneToMany(mappedBy = "aPackage")
    private List<PackageSensor> packageSensors;

    public Package() {

    }
    public Package(PackagingType packagingType, String packagingMaterial) {
        this.packagingType = packagingType;
        this.packagingMaterial = packagingMaterial;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PackagingType getPackagingType() {
        return packagingType;
    }

    public void setPackagingType(PackagingType packagingType) {
        this.packagingType = packagingType;
    }

    public String getPackagingMaterial() {
        return packagingMaterial;
    }

    public void setPackagingMaterial(String packagingMaterial) {
        this.packagingMaterial = packagingMaterial;
    }

    public List<PackageSensor> getPackageSensors() {
        return packageSensors;
    }

    public void setPackageSensors(List<PackageSensor> packageSensors) {
        this.packageSensors = packageSensors;
    }

}
