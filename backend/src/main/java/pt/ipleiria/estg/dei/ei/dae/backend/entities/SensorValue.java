package pt.ipleiria.estg.dei.ei.dae.backend.entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "sensor_values")
@NamedQuery(name = "getAllSensorValues", query = "SELECT sv FROM SensorValue sv ORDER BY sv.id")
@NamedQuery(name = "getSensorValueById", query = "SELECT sv FROM SensorValue sv WHERE sv.id = :id")
@NamedQuery(name = "getSensorValueBySensorId", query = "SELECT sv FROM SensorValue sv WHERE sv.sensor.id = :sensorId")
public class SensorValue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "TBL_METADATA_ID_SEQ")
    @Column(name="id")
    long id;

    private String value;

    @ManyToOne
    private Sensor sensor;

    @ManyToOne
    private  PackageSensor packageSensor;

    public SensorValue(){

    }

    public SensorValue(Sensor sensor, PackageSensor packageSensor ){
        this.sensor = sensor;
        this.packageSensor =packageSensor;
    }

    public long getId() {
        return id;
    }



    public Sensor getSensor() {
        return sensor;
    }

    public PackageSensor getPackageSensor() {
        return packageSensor;
    }

    public void setPackageSensor(PackageSensor packageSensor){
        this.packageSensor = packageSensor;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
