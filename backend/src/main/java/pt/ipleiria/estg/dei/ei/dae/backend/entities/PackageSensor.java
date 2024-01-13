package pt.ipleiria.estg.dei.ei.dae.backend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Null;

import java.util.List;

@Entity
@Table(name = "package_sensors")
@NamedQueries({
        @NamedQuery(
                name = "getAllPackageSensors",
                query = "SELECT c FROM PackageSensor c ORDER BY c.id, c.aPackage.id"), // JPQL
})
public class PackageSensor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "TBL_METADATA_ID_SEQ")
    @Column(name="id")
    long id;

    @ManyToMany
    private List<Sensor> sensors;

    @ManyToOne
    private Package aPackage;

    @OneToOne
    private UnitProduct unitProduct;

    private String value;

    public PackageSensor() {
    }

    public PackageSensor(List<Sensor> s, Package aPackage, UnitProduct unitProducts, String value) {
        this.sensors = s;
        this.aPackage = aPackage;
        this.unitProduct = unitProducts;
        this.value = value;
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

    public UnitProduct getUnitProduct() {
        return unitProduct;
    }

    public void setUnitProduct(UnitProduct unitProduct) {
        this.unitProduct = unitProduct;
    }

    public List<Sensor> getSensors() {
        return sensors;
    }

    public void setSensors(List<Sensor> sensors) {
        this.sensors = sensors;
    }
}
