package pt.ipleiria.estg.dei.ei.dae.backend.dtos;

import java.io.Serializable;

public class SensorValueDTO implements Serializable {

    private long id;

    private String value;

    private long sensorId;

    private long packageSensorId;

    public SensorValueDTO(long id, SensorDTO sensorDTO, String value) {
        this.id = id;
        this.value = sensorDTO.getValue();
        this.sensorId = sensorDTO.getId();
        this.value = value;

    }

    //region getters/setters
    public long getId() {
        return id;
    }

    public String getValue() {
        return value;
    }

    public long getSensorId() {
        return sensorId;
    }

    public long getPackageSensorId() {
        return packageSensorId;
    }

    //endregion
}
