package pt.ipleiria.estg.dei.ei.dae.backend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Null;

import java.util.List;

@Entity
@Table(name = "package_sensors")
@NamedQueries({
        @NamedQuery(
                name = "getAllPackageSensors",
                query = "SELECT c FROM PackageSensor c ORDER BY c.sensor.id, c.aPackage.id"), // JPQL
})
public class PackageSensor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "TBL_METADATA_ID_SEQ")
    @Column(name="id")
    long id;

    @ManyToOne
    private Sensor sensor;

    @ManyToOne
    private Package aPackage;

    @OneToMany(mappedBy = "packageSensor", cascade = CascadeType.REMOVE)
    private List<UnitProduct> unitProducts;

    private String value;

    public PackageSensor() {
    }

    public PackageSensor(Sensor sensor, Package aPackage, List<UnitProduct> unitProducts, String value) {
        this.sensor = sensor;
        this.aPackage = aPackage;
        this.unitProducts = unitProducts;
        this.value = value;
    }

    public Sensor getSensor() {
        return sensor;
    }

    public void setSensor(Sensor sensor) {
        this.sensor = sensor;
    }

    public void setValue(String s) {
        this.value = s;
    }

    public Long getId() {
        return id;
    }

    public String getValue() {
        return value;
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

    public List<UnitProduct> getUnitProducts() {
        return unitProducts;
    }

    public void setUnitProducts(List<UnitProduct> unitProducts) {
        this.unitProducts = unitProducts;
    }


    public UnitProduct getUnitProduct() {
        return unitProducts.get(0);
    }
}
