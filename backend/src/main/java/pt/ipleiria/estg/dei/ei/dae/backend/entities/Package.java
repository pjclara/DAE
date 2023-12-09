package pt.ipleiria.estg.dei.ei.dae.backend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;


@Entity
@NamedQueries({
        @NamedQuery(
                name = "getAllPackages",
                query = "SELECT p FROM Package p ORDER BY p.packageCode" // JPQL
        )
})
@Table(
        name = "packages",
        uniqueConstraints = @UniqueConstraint(columnNames = { "code" })
)
public class Package extends Versionable {

    @Id
    @Column(name = "code")
    private long packageCode;
    @NotNull
    private String packagingType;  // [1º,2º,3º(Produto) ou encomenda]
    @NotNull
    private String packagingMaterial;

    //@ManyToOne
    // private List<Sensor> sensors; // ver depois o tipo de ligação (one to many.. many to one..)
    // TODO: remove comment after fix

    // private Package transportPackage; // ????

    public Package() {
        //this.sensors =  new ArrayList<>();
    }
    public Package(long packageCode, String packagingType, String packagingMaterial) {
        this();
        this.packageCode = packageCode;
        this.packagingType = packagingType;
        this.packagingMaterial = packagingMaterial;
        //this.sensors =  new ArrayList<>(); // TODO: remove comment after fix
    }

    public long getPackageCode() {
        return packageCode;
    }

    public void setPackageCode(long packageCode) {
        this.packageCode = packageCode;
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

    /*public List<Sensor> getSensors() { // TODO: remove comment after fix
        return sensors;
    }

    public void addSensor(Sensor sensor) { // setStudent
        if (sensor == null || sensors.contains(sensor)) {
            return;
        }
        sensors.add(sensor);
    }

    public void removeSensor(Sensor sensor) {
        if (sensor == null || sensors.contains(sensor)) {
            return;
        }
        sensors.remove(sensor);
    }*/

}
