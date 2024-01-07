package pt.ipleiria.estg.dei.ei.dae.backend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;


@Entity
@Table(name = "sensors")
@NamedQuery(name = "getAllSensors", query = "SELECT s FROM Sensor s ORDER BY s.id")
public class Sensor extends Versionable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "TBL_METADATA_ID_SEQ")
    @Column(name="id")
    private long id;
    @NotNull
    private String source;  // tipo de embalagem (Produto/Encomenda) é preciso?
    @NotNull
    private String type;    // enum / tabela tipo de Sensor (Temperatura, Humidade, Pressão, integridade, localização)
    @NotNull
    private String value;
    @NotNull
    private String unit;    // enum / tabela unidade de medida (ºC, %, Pa, m/s, m, etc)
    @NotNull
    private String max;     // valor máximo aceitável
    @NotNull
    private String min;     // valor mínimo aceitável
    @NotNull
    private long timestamp;

    @ManyToOne
    @JoinTable(
            name = "sensors_package",
            joinColumns = @JoinColumn(name = "sensor_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "package_id", referencedColumnName = "id")
    )
   // @JoinColumn(name = "package_id")
    private Package packagging;

    public Sensor() {
    }

    public Sensor(String source, String type, String value, String unit, String max, String min, long timestamp) {
        this.source = source;
        this.type = type;
        this.value = value;
        this.unit = unit;
        this.max = max;
        this.min = min;
        this.timestamp = timestamp;
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

    public Package getPackagging() {
        return packagging;
    }

    public void setPackaging(Package packagging) {
        this.packagging = packagging;
    }

}
