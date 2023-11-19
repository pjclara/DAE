package pt.ipleiria.estg.dei.ei.dae.backend.entities;

import jakarta.validation.constraints.NotNull;

public class Sensor extends Versionable {
    @NotNull
    private String source;  // tipo de embalagem (Produto/Encomenda) é preciso?
    @NotNull
    private String type;    // tipo de Sensor (Temperatura, Humidade, Pressão, etc.)
    @NotNull
    private String value;
    @NotNull
    private long timestamp;
    @NotNull
    private Package packagging;

    public Sensor() {
    }

    public Sensor(String source, String type, String value, long timestamp, Package packagging) {
        this.source = source;
        this.type = type;
        this.value = value;
        this.timestamp = timestamp;
        this.packagging = packagging;
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

    public void setPackagging(Package packagging) {
        this.packagging = packagging;
    }
}
