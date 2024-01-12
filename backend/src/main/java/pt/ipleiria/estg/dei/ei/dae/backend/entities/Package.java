package pt.ipleiria.estg.dei.ei.dae.backend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;

import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "packages")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Package extends Versionable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "TBL_METADATA_ID_SEQ")
    @Column(name="id")
    private Long id;
    @NotNull
    private PackagingType packagingType;  // [1º,2º,3º(Produto) ou encomenda / transporte]
    @NotNull
    private String packagingMaterial;

    @OneToOne(mappedBy = "orderPackage")
    private Orderr order;

    @OneToMany
    private List<PackageSensor> packageSensors;

    public Package() {
        this.packageSensors =  new ArrayList<>();
    }


    public Package(PackagingType packagingType, String packagingMaterial) {
        this.packagingType = packagingType;
        this.packagingMaterial = packagingMaterial;
        this.packageSensors =  new ArrayList<>();
    }

    public Long getId() {
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

    public Orderr getOrder() {
        return order;
    }

    public void setOrder(Orderr order) {
        this.order = order;
        if (order != null) {
            order.setOrderPackage(this);
        }
    }

    public List<Sensor> getAllPackageSensors() {
        List<Sensor> sensors = new ArrayList<>();
        for (PackageSensor packageSensor : packageSensors) {
            sensors.add(packageSensor.getSensor());
        }
        return sensors;
    }

    public void setPackageSensors(List<PackageSensor> packageSensors) {
        this.packageSensors = packageSensors;
    }

    public void addPackageSensor(PackageSensor packageSensor) {
        this.packageSensors.add(packageSensor);
    }

    public void removePackageSensor(PackageSensor packageSensor) {
        this.packageSensors.remove(packageSensor);
    }

    public void clearPackageSensors() {
        this.packageSensors.clear();
    }


    public void removeSensor(Sensor sensorToRemove) {
        for (PackageSensor packageSensor : packageSensors) {
            if (packageSensor.getSensor().equals(sensorToRemove)) {
                packageSensors.remove(packageSensor);
                return;
            }
        }
    }
}
