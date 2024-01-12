package pt.ipleiria.estg.dei.ei.dae.backend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

@Entity
@NamedQuery(name = "getAllUnitProducts", query = "SELECT up FROM UnitProduct up ORDER BY up.product.name")
@NamedQuery(name = "getUnitProductByProductId", query = "SELECT up FROM UnitProduct up WHERE up.product.id = :productId")
@NamedQuery(name = "getUnitProductAvailableByProductId", query = "SELECT up FROM UnitProduct up WHERE up.product.id = :productId AND up.available = :available")
public class UnitProduct extends Versionable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "TBL_METADATA_ID_SEQ")
    @Column(name="id")
    long id;

    @OneToOne
    private Product product;

    @NotNull
    UUID serialNumber;

    @NotNull
    boolean available = true;

    public UnitProduct() {
    }

    public UnitProduct(Product product,  UUID serialNumber) {
        this.product = product;
        this.serialNumber = serialNumber;
        this.available = true;
    }
    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public UUID getSerialNumber() {
        return serialNumber;
    }

    public long getId() {
        return id;
    }

    public void setAvailable(boolean b) {
        this.available = b;
    }

    public boolean getAvailable() {
        return available;
    }
}
