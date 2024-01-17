package pt.ipleiria.estg.dei.ei.dae.backend.dtos;

import pt.ipleiria.estg.dei.ei.dae.backend.entities.PackageOrder;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.PackagingType;

import java.io.Serializable;
import java.util.List;

public class PackageOrderDTO implements Serializable {

    private Long id;
    private PackagingType packagingType;
    private String packagingMaterial;

    public PackageOrderDTO() {

    }
    public PackageOrderDTO(Long id, PackagingType packagingType, String packagingMaterial) {
        this.id = id;
        this.packagingType = packagingType;
        this.packagingMaterial = packagingMaterial;
    }

    public PackageOrderDTO(PackagingType packagingType, String packagingMaterial) {
        this.packagingType = packagingType;
        this.packagingMaterial = packagingMaterial;
    }

    // getters and setters
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

}
