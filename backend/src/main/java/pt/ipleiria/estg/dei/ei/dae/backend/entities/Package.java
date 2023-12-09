package pt.ipleiria.estg.dei.ei.dae.backend.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotNull;

import java.util.List;

@Entity
public class Package extends Versionable{
    @Id
    private Long id;
    @NotNull
    private String packagingType;  // [1º,2º,3º(Produto) ou encomenda]
    @NotNull
    private String packagingMaterial;
    @OneToMany
    private List<Sensor> sensorData;

    @OneToOne(mappedBy = "productPackage")
    private Product product;

    public Package(Long id, String packagingType, String packagingMaterial, List<Sensor> sensorData, Product product) {
        this.id = id;
        this.packagingType = packagingType;
        this.packagingMaterial = packagingMaterial;
        this.sensorData = sensorData;
        this.product = product;
    }

    public Package() {
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Long getId() {
        return id;
    }

    public String getPackagingType() {
        return packagingType;
    }

    public void setPackagingType(String packagingType) {
        this.packagingType = packagingType;
    }

    public String getPackagingMaterial() {
        return packagingMaterial;
    }

    public void setPackagingMaterial(String packagingMaterial) {
        this.packagingMaterial = packagingMaterial;
    }

    public List<Sensor> getSensorData() {
        return sensorData;
    }

    public void setSensorData(List<Sensor> sensorData) {
        this.sensorData = sensorData;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
