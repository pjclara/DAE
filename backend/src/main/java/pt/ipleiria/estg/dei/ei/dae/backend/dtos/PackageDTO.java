package pt.ipleiria.estg.dei.ei.dae.backend.dtos;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.Sensor;

import java.util.ArrayList;
import java.util.List;

public class PackageDTO {
    private Long id;
    private String packagingType;
    private String packagingMaterial;
    private List<SensorDTO> sensors;

    public PackageDTO() {
        this.sensors = new ArrayList<>();
    }

    public PackageDTO(Long id, String packagingType, String packagingMaterial) {
        this.id = id;
        this.packagingType = packagingType;
        this.packagingMaterial = packagingMaterial;
        this.sensors = new ArrayList<>();
    }

    public long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public List<SensorDTO> getSensors() {
        return sensors;
    }

    public void setSensors(List<SensorDTO> sensorData) {
        this.sensors = sensorData;
    }
}
