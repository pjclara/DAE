package pt.ipleiria.estg.dei.ei.dae.backend.dtos;

import pt.ipleiria.estg.dei.ei.dae.backend.entities.Sensor;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.SensorValue;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

public class SensorValueDTO implements Serializable {

    private long id;

    private String value;

    private SensorDTO sensorDTO;

    private long packageSensorId;

    public SensorValueDTO(long id, SensorDTO sensorDTO, String value) {
        this.id = id;
        this.sensorDTO = sensorDTO;
        this.value = value;

    }

    //region getters/setters
    public long getId() {
        return id;
    }

    public String getValue() {
        return value;
    }

    public SensorDTO getSensorDTO() {
        return sensorDTO;
    }

    public long getPackageSensorId() {
        return packageSensorId;
    }

   public static List<SensorValueDTO> toDTOs(List<SensorValue> sensorValues) {
        return sensorValues.stream().map(SensorValueDTO::toDTO).collect(Collectors.toList());
    }

    public static SensorValueDTO toDTO(SensorValue sensorValue) {
        return new SensorValueDTO(
                sensorValue.getId(),
                SensorDTO.toDTO(sensorValue.getSensor()),
                sensorValue.getValue()
        );
    }
}
