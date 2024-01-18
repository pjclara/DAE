package pt.ipleiria.estg.dei.ei.dae.backend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Null;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "package_sensors")
@NamedQueries({
        @NamedQuery(
                name = "getAllPackageSensors",
                query = "SELECT c FROM PackageSensor c ORDER BY c.id, c.aPackage.id"),
        @NamedQuery(name = "getPackageSensorByUnitProductId",
                query = "SELECT c FROM PackageSensor c WHERE c.unitProduct.id = :unitProductId"),
})

public class PackageSensor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "TBL_METADATA_ID_SEQ")
    @Column(name="id")
    long id;

    @OneToMany
    private List<SensorValue> sensorValues;

    @ManyToOne
    private Package aPackage;

    @OneToOne
    private UnitProduct unitProduct;

    @OneToOne
    private Orderr order;

    public PackageSensor() {
        this.sensorValues = new ArrayList<>();

    }

    public PackageSensor(Package aPackage, UnitProduct unitProducts) {
        this.aPackage = aPackage;
        this.unitProduct = unitProducts;
        this.sensorValues = new ArrayList<>();
    }

    public PackageSensor(Package aPackage, Orderr order) {
        this.aPackage = aPackage;
        this.order = order;
        this.sensorValues = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public Package getPackagging() {
        return aPackage;
    }

    public void setPackagging(Package aPackage) {
        this.aPackage = aPackage;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Package getaPackage() {
        return aPackage;
    }

    public void setaPackage(Package aPackage) {
        this.aPackage = aPackage;
    }

    public UnitProduct getUnitProduct() {
        return unitProduct;
    }

    public void setUnitProduct(UnitProduct unitProduct) {
        this.unitProduct = unitProduct;
    }

    public List<SensorValue> getSensorValues(){
        return sensorValues;
    }

    public void addSensorValue(SensorValue sensorValue) {
        sensorValue.setPackageSensor(this);
        this.sensorValues.add(sensorValue);
    }

    public void addSensor(Sensor sensor) {
        SensorValue sensorValue = new SensorValue(sensor, this);
        sensorValue.setPackageSensor(this);
        this.sensorValues.add(sensorValue);
    }

    public void removeSensorValue(SensorValue sensorValue) {
        this.sensorValues.remove(sensorValue);
    }

    public void setSensorValues(List<SensorValue> sensorValues) {
        this.sensorValues = sensorValues;
    }

    public Orderr getOrder() {
        return order;
    }

    public void setOrder(Orderr order) {
        this.order = order;
    }

    public void removeOrder(Orderr order) {
        this.order = null;
    }

    public void removeUnitProduct(UnitProduct unitProduct) {
        this.unitProduct = null;
    }

    public void setPackageProduct(PackageProduct packageProduct) {
        this.aPackage = packageProduct;
    }
}
