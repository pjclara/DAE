package pt.ipleiria.estg.dei.ei.dae.backend.dtos;

import pt.ipleiria.estg.dei.ei.dae.backend.entities.Sensor;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

public class SensorDTO implements Serializable {
    
    private long id;
    
    private String source;
    
    private String type;
    
    private String value;
    
    private String unit;
    
    private String max;
    
    private String min;
    
    private long timestamp;
    
    private long packageId;

    public SensorDTO() {
    }

    public SensorDTO(Long id, String source, String type, String value, String unit, String max, String min, long timestamp) {
        this.id = id;
        this.source = source;
        this.type = type;
        this.value = value;
        this.unit = unit;
        this.max = max;
        this.min = min;
        this.timestamp = timestamp;
    }
    public SensorDTO(long id, String source, String type, String value, String unit, String max, String min, long timestamp, long packageId) {
        this.id = id;
        this.source = source;
        this.type = type;
        this.value = value;
        this.unit = unit;
        this.max = max;
        this.min = min;
        this.timestamp = timestamp;
        this.packageId = packageId;
    }



    public Long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getMax() {
        return max;
    }

    public void setMax(String max) {
        this.max = max;
    }

    public String getMin() {
        return min;
    }

    public void setMin(String min) {
        this.min = min;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public long getPackageId() {
        return packageId;
    }

    public void setPackageId(long packageId) {
        this.packageId = packageId;
    }

    public static SensorDTO from(Sensor sensor) {
        return new SensorDTO(
                sensor.getId(),
                sensor.getSource(),
                sensor.getType(),
                sensor.getValue(),
                sensor.getUnit(),
                sensor.getMax(),
                sensor.getMin(),
                sensor.getTimestamp(),
                sensor.getPackagging().getId()
        );
    }

    public static List<SensorDTO> from(List<Sensor> sensors) {
        return sensors.stream().map(SensorDTO::from).collect(Collectors.toList());
    }
}
