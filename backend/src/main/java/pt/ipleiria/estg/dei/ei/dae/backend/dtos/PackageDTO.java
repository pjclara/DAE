package pt.ipleiria.estg.dei.ei.dae.backend.dtos;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.Sensor;

import java.util.ArrayList;
import java.util.List;

public class PackageDTO {

    private String packagingType;
    private String packagingMaterial;
    private List<Sensor> sensorData; // passar para SensorDTO -- mudar depois no construtor
    // TODO: depois passas de Sensor para SensorDTO

    public PackageDTO() {
        this.sensorData = new ArrayList<>();
    }
    public PackageDTO(String packagingType, String packagingMaterial, List<Sensor> sensorData) { // passar para SensorDTO
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
