package pt.ipleiria.estg.dei.ei.dae.backend.dtos;

import java.io.Serializable;
import java.util.UUID;

public class UnitProductDTO implements Serializable {
    long id;

    private UUID serialNumber;

    private ProductDTO productDTO;


    public UnitProductDTO() {
    }

    public UnitProductDTO(long id, UUID serialNumber,ProductDTO productDTO) {
        this.id = id;
        this.serialNumber = serialNumber;
        this.productDTO = productDTO;
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
        return productDTO;
    }
    public void setProductDTO(ProductDTO productDTO) {
        this.productDTO = productDTO;
    }

}
