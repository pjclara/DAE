package pt.ipleiria.estg.dei.ei.dae.backend.entities;

import jakarta.validation.constraints.NotNull;

public class Sensor extends Versionable {
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
    @NotNull
    private Package packagging;

    public Sensor() {
    }

    public Sensor(String source, String type, String value, String unit, String max, String min, long timestamp, Package packagging) {
        this.source = source;
        this.type = type;
        this.value = value;
        this.unit = unit;
        this.max = max;
        this.min = min;
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
