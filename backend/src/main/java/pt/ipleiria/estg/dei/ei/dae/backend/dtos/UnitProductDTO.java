package pt.ipleiria.estg.dei.ei.dae.backend.dtos;

import pt.ipleiria.estg.dei.ei.dae.backend.entities.UnitProduct;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

public class UnitProductDTO implements Serializable {
    long id;

    private UUID serialNumber;

    private ProductDTO product;

    private boolean available;

    private PackageSensorDTO packageSensorDTO;

    public UnitProductDTO(long id, UUID productDTO, boolean available, ProductDTO dto, PackageSensorDTO packageSensorDTO) {
        this.id = id;
        this.serialNumber = productDTO;
        this.available = available;
        this.product = dto;
        this.packageSensorDTO = packageSensorDTO;
    }

    public static List<UnitProduct> unitProducts(List<UnitProduct> unitProducts) {
        return unitProducts;
    }

    // getters and setters
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    public UUID getSerialNumber() {
        return serialNumber;
    }
    public void setSerialNumber(UUID serialNumber) {
        this.serialNumber = serialNumber;
    }

    public ProductDTO getProductDTO() {
        return product;
    }
    public void setProductDTO(ProductDTO productDTO) {
        this.product = productDTO;
    }

    public boolean getAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public PackageSensorDTO getPackageSensorDTO() {
        return packageSensorDTO;
    }

    public void setPackageSensorDTO(PackageSensorDTO packageSensorDTO) {
        this.packageSensorDTO = packageSensorDTO;
    }

}
