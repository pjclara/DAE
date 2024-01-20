package pt.ipleiria.estg.dei.ei.dae.backend.dtos;

import pt.ipleiria.estg.dei.ei.dae.backend.entities.Sensor;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.SensorValue;

import java.io.Serializable;

public class SensorValueDTO implements Serializable {

    private long id;

    private String value;

    private SensorDTO sensorDTO;

    private long packageSensorId;

    public SensorValueDTO() {

    }

    public SensorValueDTO(long id, SensorDTO sensorDTO, String value) {
        this.id = id;
        this.sensorDTO = sensorDTO;
        this.value = value;

    }

    //region getters/setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public SensorDTO getSensorDTO() {
        return sensorDTO;
    }

    public void setSensorDTO(SensorDTO sensorDTO) {
        this.sensorDTO = sensorDTO;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public long getPackageSensorId() {
        return packageSensorId;
    }

    public void setPackageSensorId(long packageSensorId) {
        this.packageSensorId = packageSensorId;
    }

    public static SensorValueDTO toDTO(SensorValue sensorValue) {
        SensorDTO sensorDTO = SensorDTO.toDTO(sensorValue.getSensor());

        SensorValueDTO dto = new SensorValueDTO();
        dto.setId(sensorValue.getId());
        dto.setSensorDTO(sensorDTO);
        dto.setValue(sensorValue.getValue());
        dto.setPackageSensorId(sensorValue.getPackageSensor().getId());

        return dto;
    }
    //endregion
}
