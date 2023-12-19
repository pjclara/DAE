package pt.ipleiria.estg.dei.ei.dae.backend.dtos;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.PackagingType;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.Sensor;

import java.util.ArrayList;
import java.util.List;

public class PackageDTO {
    private Long id;
    private PackagingType packagingType;
    private String packagingMaterial;
    private List<SensorDTO> sensors;

    public PackageDTO() {
        this.sensors = new ArrayList<>();
    }

    public PackageDTO(Long id, PackagingType packagingType, String packagingMaterial) {
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

    public List<SensorDTO> getSensors() {
        return sensors;
    }

    public void addSensor(SensorDTO sensorDTO) {
        if (sensorDTO != null) {
            this.sensors.add(sensorDTO);
        }
    }

    public void removeSensor(SensorDTO sensorDTO) {
        this.sensors.remove(sensorDTO);
    }

    public void editSensor(SensorDTO oldSensor, SensorDTO newSensor) {
        int index = this.sensors.indexOf(oldSensor);
        if (index != -1) {
            this.sensors.set(index, newSensor);
        }
    }

}
