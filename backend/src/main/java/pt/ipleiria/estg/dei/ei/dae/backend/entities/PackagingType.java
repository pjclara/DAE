package pt.ipleiria.estg.dei.ei.dae.backend.entities;

public enum PackagingType {
    PRIMARY("Primary"),
    SECONDARY("Secondary"),
    THIRD("Trird"),
    TRANSPORT("Transport");


    private final String nome;

    PackagingType(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }
}
