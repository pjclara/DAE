package pt.ipleiria.estg.dei.ei.dae.backend.entities;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public class Package extends Versionable{
    @NotNull
    private String packagingType;  // [1º,2º,3º(Produto) ou encomenda]
    @NotNull
    private String packagingMaterial;

    private List<Sensor> sensorData;

    public Package() {
    }
    public Package(String packagingType, String packagingMaterial, List<Sensor> sensorData) {
        this.packagingType = packagingType;
        this.packagingMaterial = packagingMaterial;
        this.sensorData = sensorData;
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
}
