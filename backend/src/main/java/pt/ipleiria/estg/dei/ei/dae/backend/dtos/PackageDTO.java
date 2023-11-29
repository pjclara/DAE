package pt.ipleiria.estg.dei.ei.dae.backend.dtos;

import jakarta.validation.constraints.NotNull;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.Sensor;

import java.util.ArrayList;
import java.util.List;

public class PackageDTO {

    private String packagingType;
    private String packagingMaterial;
    private List<Sensor> sensorData; // passar para SensorDTO -- mudar depois no construtor

    public PackageDTO() {
        this.sensorData = new ArrayList<>();
    }
    public PackageDTO(String packagingType, String packagingMaterial, List<Sensor> sensorData) {
        this.packagingType = packagingType;
        this.packagingMaterial = packagingType;
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
