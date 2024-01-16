package pt.ipleiria.estg.dei.ei.dae.backend.dtos;

import pt.ipleiria.estg.dei.ei.dae.backend.entities.PackageProduct;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.PackagingType;

import java.io.Serializable;
import java.util.List;

public class PackageProductDTO implements Serializable {

    private Long id;
    private PackagingType packagingType;
    private String packagingMaterial;

    public PackageProductDTO() {

    }

    public PackageProductDTO(PackagingType packagingType, String packagingMaterial) {
        this.packagingType = packagingType;
        this.packagingMaterial = packagingMaterial;
    }

    public PackageProductDTO(long id, PackagingType packagingType, String packagingMaterial) {
        this.id = id;
        this.packagingType = packagingType;
        this.packagingMaterial = packagingMaterial;
    }

    public List<PackageProductDTO> toDTOs(List<PackageProduct> packageProducts) {
        return packageProducts.stream().map(this::toDTO).collect(java.util.stream.Collectors.toList());
    }

    public PackageProductDTO toDTO(PackageProduct packageProduct) {
        return new PackageProductDTO(
                packageProduct.getId(),
                packageProduct.getPackagingType(),
                packageProduct.getPackagingMaterial()
        );

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
