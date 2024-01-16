package pt.ipleiria.estg.dei.ei.dae.backend.dtos;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.*;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.Package;

import java.util.List;
import java.util.stream.Collectors;

public class PackageDTO implements java.io.Serializable {
    private Long id;
    private PackagingType packagingType;
    private String packagingMaterial;

    public PackageDTO() {

    }

    public PackageDTO(long id, PackagingType packagingType, String packagingMaterial) {
        this.id = id;
        this.packagingType = packagingType;
        this.packagingMaterial = packagingMaterial;
    }

    public long getId() {
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


    public static PackageDTO from(Package package_) {
        return new PackageDTO(
        );
    }

    public static List<PackageDTO> from(List<Package> packages) {
        return packages.stream().map(PackageDTO::from).collect(Collectors.toList());
    }
}
