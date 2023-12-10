package pt.ipleiria.estg.dei.ei.dae.backend.dtos;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.Sensor;

import java.util.ArrayList;
import java.util.List;

public class PackageDTO {
    private Long id;
    private String packagingType;
    private String packagingMaterial;
    private List<Sensor> sensors; // passar para SensorDTO -- mudar depois no construtor
    // TODO: depois passas de <Sensor> para <SensorDTO>

    public PackageDTO(Long id, String packagingType, String packagingMaterial) { //TODO: passar para SensorDTO
        this.id = id;
        this.packagingType = packagingType;
        this.packagingMaterial = packagingMaterial;
        this.sensors = new ArrayList<>();
    }

    public long getPackagingId() {
        return id;
    }

    public void setPackagingId(Long packagingId) {
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

    public List<Sensor> getSensors() {
        return sensors;
    }

    public void setSensors(List<Sensor> sensorData) {
        this.sensors = sensorData;
    }
}
