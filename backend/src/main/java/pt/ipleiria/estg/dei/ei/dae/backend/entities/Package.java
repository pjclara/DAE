package pt.ipleiria.estg.dei.ei.dae.backend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;

@Entity
@NamedQueries({
        @NamedQuery(
                name = "getAllPackages",
                query = "SELECT p FROM Package p ORDER BY p.packagingType" // JPQL
        )
})

public class Package extends Versionable{

    @Id
    private long id; // TODO: ver se o id é tambem passado para o construtor
    @NotNull
    private String packagingType;  // [1º,2º,3º(Produto) ou encomenda]
    @NotNull
    private String packagingMaterial;

    private List<Sensor> sensors; // ver depois o tipo de ligação (one to many....)

    public Package() {
        this.sensors =  new ArrayList<>();
    }
    public Package(String packagingType, String packagingMaterial) {
        this.packagingType = packagingType;
        this.packagingMaterial = packagingMaterial;
        this.sensors =  new ArrayList<>();
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

    public List<Sensor> getSensors() {
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
    }

    public void setId(long id) {
        this.id = id;
    }
    public long getId() {
        return id;
    }
}
